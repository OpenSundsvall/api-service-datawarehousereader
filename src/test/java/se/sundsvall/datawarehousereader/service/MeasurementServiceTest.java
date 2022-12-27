package se.sundsvall.datawarehousereader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.zalando.problem.Status.NOT_IMPLEMENTED;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_HEATING;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.api.model.Category.WASTE_MANAGEMENT;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.DAY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.service.logic.DistrictHeatingMeasurementProvider;
import se.sundsvall.datawarehousereader.service.logic.ElectricityMeasurementProvider;
import se.sundsvall.datawarehousereader.service.logic.PartyProvider;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

	@Mock
	private DistrictHeatingMeasurementProvider districtHeatingMeasurementProviderMock;

	@Mock
	private ElectricityMeasurementProvider electricityMeasurementProviderMock;

	@Mock
	private PartyProvider partyProviderMock;

	@InjectMocks
	private MeasurementService service;

	@Test
	void testGetMeasurementsDistrictHeatingMonthPartyIdIsNotSet() {
		final var category = DISTRICT_HEATING;
		final var aggregateOn = MONTH;
		final var fromDateTimeOffset = OffsetDateTime.now().minusMonths(1L);
		final var toDateTimeOffset = OffsetDateTime.now();
		final var fromDateTimeLocal = fromDateTimeOffset.toLocalDateTime();
		final var toDateTimeLocal = toDateTimeOffset.toLocalDateTime();
		final var searchParams = MeasurementParameters.create();

		searchParams.setFromDateTime(fromDateTimeOffset);
		searchParams.setToDateTime(toDateTimeOffset);

		when(districtHeatingMeasurementProviderMock.getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams)).thenReturn(MeasurementResponse.create());

		final var measurementResponse = service.getMeasurements(category, aggregateOn, searchParams);

		verify(districtHeatingMeasurementProviderMock).getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams);

		assertThat(measurementResponse).isNotNull().isEqualTo(MeasurementResponse.create());
		verifyNoInteractions(partyProviderMock);
	}

	@Test
	void testGetMeasurementsDistrictHeatingMonthPartyIdIsSet() {
		final var category = DISTRICT_HEATING;
		final var aggregateOn = MONTH;
		final var partyId = "partyId";
		final var legalId = "legalId";
		final var fromDateTimeOffset = OffsetDateTime.now().minusMonths(1L);
		final var toDateTimeOffset = OffsetDateTime.now();
		final var fromDateTimeLocal = fromDateTimeOffset.toLocalDateTime();
		final var toDateTimeLocal = toDateTimeOffset.toLocalDateTime();
		final var searchParams = MeasurementParameters.create();

		searchParams.setFromDateTime(fromDateTimeOffset);
		searchParams.setToDateTime(toDateTimeOffset);
		searchParams.setPartyId("partyId");

		when(partyProviderMock.translateToLegalId(partyId)).thenReturn(legalId);
		when(districtHeatingMeasurementProviderMock.getMeasurements(legalId, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams)).thenReturn(MeasurementResponse.create());

		final var measurementResponse = service.getMeasurements(category, aggregateOn, searchParams);

		verify(districtHeatingMeasurementProviderMock).getMeasurements(legalId, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams);
		verify(partyProviderMock).translateToLegalId(partyId);

		assertThat(measurementResponse).isNotNull().isEqualTo(MeasurementResponse.create());
	}

	@Test
	void testGetMeasurementsElectricityMonthPartyIdIsNotSet() {
		final var category = ELECTRICITY;
		final var aggregateOn = MONTH;
		final var fromDateTimeOffset = OffsetDateTime.now().minusMonths(1L);
		final var toDateTimeOffset = OffsetDateTime.now();
		final var fromDateTimeLocal = fromDateTimeOffset.toLocalDateTime();
		final var toDateTimeLocal = toDateTimeOffset.toLocalDateTime();
		final var searchParams = MeasurementParameters.create();

		searchParams.setFromDateTime(fromDateTimeOffset);
		searchParams.setToDateTime(toDateTimeOffset);

		when(electricityMeasurementProviderMock.getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams)).thenReturn(MeasurementResponse.create());

		final var measurementResponse = service.getMeasurements(category, aggregateOn, searchParams);

		verify(electricityMeasurementProviderMock).getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams);

		assertThat(measurementResponse).isNotNull().isEqualTo(MeasurementResponse.create());
		verifyNoInteractions(partyProviderMock);
	}

	@Test
	void testGetMeasurementsElectricityDayPartyIdIsNotSet() {
		final var category = ELECTRICITY;
		final var aggregateOn = DAY;
		final var fromDateTimeOffset = OffsetDateTime.now().minusMonths(1L);
		final var toDateTimeOffset = OffsetDateTime.now();
		final var fromDateTimeLocal = fromDateTimeOffset.toLocalDateTime();
		final var toDateTimeLocal = toDateTimeOffset.toLocalDateTime();
		final var searchParams = MeasurementParameters.create();

		searchParams.setFromDateTime(fromDateTimeOffset);
		searchParams.setToDateTime(toDateTimeOffset);

		when(electricityMeasurementProviderMock.getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams)).thenReturn(MeasurementResponse.create());

		final var measurementResponse = service.getMeasurements(category, aggregateOn, searchParams);

		verify(electricityMeasurementProviderMock).getMeasurements(null, aggregateOn, fromDateTimeLocal, toDateTimeLocal, searchParams);

		assertThat(measurementResponse).isNotNull().isEqualTo(MeasurementResponse.create());
		verifyNoInteractions(partyProviderMock);
	}

	@Test
	void testProblemIsThrownWhenNotSupportedCategory() {
		final var category = WASTE_MANAGEMENT;
		final var aggregateOn = DAY;
		final var searchParams = MeasurementParameters.create();

		ThrowableProblem e = assertThrows(ThrowableProblem.class, () -> service.getMeasurements(category, aggregateOn, searchParams));
		assertThat(e.getStatus()).isEqualTo(NOT_IMPLEMENTED);
		assertThat(e.getMessage()).isEqualTo("Not Implemented: category 'WASTE_MANAGEMENT'");

		verifyNoInteractions(partyProviderMock, electricityMeasurementProviderMock, districtHeatingMeasurementProviderMock);
	}
}
