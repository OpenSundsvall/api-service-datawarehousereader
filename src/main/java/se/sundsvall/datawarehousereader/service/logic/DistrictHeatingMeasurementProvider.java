package se.sundsvall.datawarehousereader.service.logic;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_HEATING;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;
import static se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper.decorateMeasurement;
import static se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper.toMeasurementResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.Measurement;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementMetaData;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.MeasurementDistrictHeatingMonthRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementDistrictHeatingMonthEntity;
import se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper;

@Component
public class DistrictHeatingMeasurementProvider {

	@Autowired
	private MeasurementDistrictHeatingMonthRepository districtHeatingMonthRepository;

	private static final String AGGREGATION_NOT_IMPLEMENTED = "aggregation '%s' and category '%s'";
	private static final String READING_SEQUENCE_KEY = "readingSequence";
	private static final String FEED_TYPE_ID_KEY = "feedTypeId";

	public MeasurementResponse getMeasurements(String legalId, Aggregation aggregation, LocalDateTime fromDateTime, LocalDateTime toDateTime, MeasurementParameters searchParams) {
		if (aggregation != MONTH) {
			throw Problem.valueOf(Status.NOT_IMPLEMENTED, String.format(AGGREGATION_NOT_IMPLEMENTED, aggregation, DISTRICT_HEATING));
		}

		var matches = districtHeatingMonthRepository.findAllMatching(legalId, searchParams.getFacilityId(), fromDateTime, toDateTime,
			of(searchParams.getPage() - 1, searchParams.getLimit(), searchParams.sort()));

		// If page larger than last page is requested, an empty list is returned otherwise the current page
		List<Measurement> measurements = matches.getTotalPages() < searchParams.getPage() ? Collections.emptyList() : toMeasurements(matches.getContent(), searchParams, aggregation);
		
		return toMeasurementResponse(searchParams, matches.getTotalPages(), matches.getTotalElements(), measurements);
	}

	private List<Measurement> toMeasurements(List<MeasurementDistrictHeatingMonthEntity> entities, MeasurementParameters searchParams, Aggregation aggregation) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.filter(Objects::nonNull)
			.map(this::toMeasurement)
			.map(measurement -> decorateMeasurement(measurement, searchParams.getPartyId(), aggregation, DISTRICT_HEATING))
			.toList();
	}

	private Measurement toMeasurement(MeasurementDistrictHeatingMonthEntity entity) {
		return MeasurementMapper.toMeasurement(entity)
			.withMetaData(toMetadata(entity));
	}

	private List<MeasurementMetaData> toMetadata(MeasurementDistrictHeatingMonthEntity entity) {
		final var readingSequence = MeasurementMetaData.create().withKey(READING_SEQUENCE_KEY).withValue(toString(entity.getReadingSequence()));
		final var feedTypeId = MeasurementMetaData.create().withKey(FEED_TYPE_ID_KEY).withValue(toString(entity.getFeedTypeId()));
		return List.of(readingSequence, feedTypeId);
	}

	private String toString(Integer value) {
		return ofNullable(value)
			.map(String::valueOf)
			.orElse(null);
	}
}
