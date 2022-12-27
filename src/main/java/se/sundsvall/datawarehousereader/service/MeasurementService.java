package se.sundsvall.datawarehousereader.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.service.logic.DistrictHeatingMeasurementProvider;
import se.sundsvall.datawarehousereader.service.logic.ElectricityMeasurementProvider;
import se.sundsvall.datawarehousereader.service.logic.PartyProvider;

@Service
public class MeasurementService {
	private static final String CATEGORY_NOT_IMPLEMENTED = "category '%s'";
	
	@Autowired
	private DistrictHeatingMeasurementProvider districtHeatingMeasurementProvider;

	@Autowired
	private ElectricityMeasurementProvider electricityMeasurementProvider;

	@Autowired
	private PartyProvider partyProvider;
	
	public MeasurementResponse getMeasurements(Category category, Aggregation aggregateOn, MeasurementParameters parameters) {
		var legalId = Optional.ofNullable(parameters.getPartyId()).map(partyProvider::translateToLegalId).orElse(null);
		var fromDateTime = Optional.ofNullable(parameters.getFromDateTime()).map(OffsetDateTime::toLocalDateTime).orElse(null);
		var toDateTime = Optional.ofNullable(parameters.getToDateTime()).map(OffsetDateTime::toLocalDateTime).orElse(null);

		return switch (category) {
			case DISTRICT_HEATING -> districtHeatingMeasurementProvider.getMeasurements(legalId, aggregateOn, fromDateTime, toDateTime, parameters);
			case ELECTRICITY -> electricityMeasurementProvider.getMeasurements(legalId, aggregateOn, fromDateTime, toDateTime, parameters);
			default -> throw Problem.valueOf(Status.NOT_IMPLEMENTED, String.format(CATEGORY_NOT_IMPLEMENTED, category));
		};
	}
}
