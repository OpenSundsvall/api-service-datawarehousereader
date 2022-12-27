package se.sundsvall.datawarehousereader.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static se.sundsvall.dept44.util.DateUtils.toOffsetDateTimeWithLocalOffset;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.MetaData;
import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.Measurement;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.DefaultMeasurementAttributesInterface;

public class MeasurementMapper {

	private MeasurementMapper() {}

	public static List<Measurement> toMeasurements(List<? extends DefaultMeasurementAttributesInterface> entities, MeasurementParameters searchParams, Aggregation aggregation, Category category) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.filter(Objects::nonNull)
			.map(MeasurementMapper::toMeasurement)
			.map(measurement -> decorateMeasurement(measurement, searchParams.getPartyId(), aggregation, category))
			.toList();
	}

	public static Measurement toMeasurement(DefaultMeasurementAttributesInterface entity) {
		return Measurement.create()
			.withUnit(entity.getUnit())
			.withMeasurementType(entity.getFeedType())
			.withFacilityId(entity.getFacilityId())
			.withUnit(entity.getUnit())
			.withPartyId(entity.getUuid())
			.withInterpolation(entity.getInterpolation())
			.withValue(entity.getUsage())
			.withTimestamp(toOffsetDateTimeWithLocalOffset((entity.getMeasurementTimestamp())));
	}

	public static Measurement decorateMeasurement(Measurement measurement, String partyId, Aggregation aggregation, Category category) {
		return measurement.getPartyId() == null && StringUtils.isNotEmpty(partyId) ? measurement.withPartyId(partyId).withAggregatedOn(aggregation).withCategory(category) : measurement.withAggregatedOn(aggregation).withCategory(category);
	}

	public static MeasurementResponse toMeasurementResponse(MeasurementParameters parameters, int totalPages, long totalElements, List<Measurement> measurements) {
		return MeasurementResponse.create()
			.withMeasurements(measurements)
			.withMetaData(MetaData.create()
				.withPage(parameters.getPage())
				.withSortBy(parameters.getSortBy())
				.withSortDirection(parameters.getSortDirection())
				.withTotalPages(totalPages)
				.withTotalRecords(totalElements)
				.withCount(measurements.size())
				.withLimit(parameters.getLimit()));
	}
}
