package se.sundsvall.datawarehousereader.service.logic;

import static org.springframework.data.domain.PageRequest.of;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.DAY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;
import static se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper.toMeasurementResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.Measurement;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.MeasurementElectricityDayRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.MeasurementElectricityMonthRepository;
import se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper;

@Component
public class ElectricityMeasurementProvider {

	@Autowired
	private MeasurementElectricityMonthRepository electricityMonthRepository;

	@Autowired
	private MeasurementElectricityDayRepository electricityDayRepositoryRepository;

	private static final String AGGREGATION_NOT_IMPLEMENTED = "aggregation '%s' and category '%s'";

	public MeasurementResponse getMeasurements(String legalId, Aggregation aggregateOn, LocalDateTime fromDateTime, LocalDateTime toDateTime, MeasurementParameters parameters) {

		return switch (aggregateOn) {
			case DAY -> getElectricityDay(legalId, fromDateTime, toDateTime, parameters);
			case MONTH -> getElectricityMonth(legalId, fromDateTime, toDateTime, parameters);
			default -> throw Problem.valueOf(Status.NOT_IMPLEMENTED, String.format(AGGREGATION_NOT_IMPLEMENTED, aggregateOn, ELECTRICITY));
		};
	}

	private MeasurementResponse getElectricityMonth(String legalId, LocalDateTime fromDateTime, LocalDateTime toDateTime, MeasurementParameters parameters) {
		var matches = electricityMonthRepository.findAllMatching(legalId, parameters.getFacilityId(), fromDateTime, toDateTime,
			of(parameters.getPage() - 1, parameters.getLimit(), parameters.sort()));

		// If page larger than last page is requested, an empty list is returned otherwise the current page
		List<Measurement> measurements = matches.getTotalPages() < parameters.getPage() ? Collections.emptyList() : MeasurementMapper.toMeasurements(matches.getContent(), parameters, MONTH, ELECTRICITY);

		return toMeasurementResponse(parameters, matches.getTotalPages(), matches.getTotalElements(), measurements);
	}

	private MeasurementResponse getElectricityDay(String legalId, LocalDateTime fromDateTime, LocalDateTime toDateTime, MeasurementParameters parameters) {
		var matches = electricityDayRepositoryRepository.findAllMatching(legalId, parameters.getFacilityId(), fromDateTime, toDateTime,
			of(parameters.getPage() - 1, parameters.getLimit(), parameters.sort()));

		// If page larger than last page is requested, an empty list is returned otherwise the current page
		List<Measurement> measurements = matches.getTotalPages() < parameters.getPage() ? Collections.emptyList() : MeasurementMapper.toMeasurements(matches.getContent(), parameters, DAY, ELECTRICITY);

		return toMeasurementResponse(parameters, matches.getTotalPages(), matches.getTotalElements(), measurements);
	}
}
