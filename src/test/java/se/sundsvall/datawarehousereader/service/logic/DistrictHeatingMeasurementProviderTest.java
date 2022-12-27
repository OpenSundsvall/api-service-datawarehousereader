package se.sundsvall.datawarehousereader.service.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.zalando.problem.Status.NOT_IMPLEMENTED;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.HOUR;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.MeasurementDistrictHeatingMonthRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementDistrictHeatingMonthEntity;

@ExtendWith(MockitoExtension.class)
class DistrictHeatingMeasurementProviderTest {

	@Mock
	private MeasurementDistrictHeatingMonthRepository districtHeatingMonthRepositoryMock;

	@Mock
	private Page<MeasurementDistrictHeatingMonthEntity> pageMock;

	@Mock
	private MeasurementDistrictHeatingMonthEntity entityMock;

	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;

	@Captor
	private ArgumentCaptor<String> customerOrgNrCaptor;

	@Captor
	private ArgumentCaptor<String> facilityIdCaptor;

	@Captor
	private ArgumentCaptor<LocalDateTime> fromDateCaptor;

	@Captor
	private ArgumentCaptor<LocalDateTime> toDateCaptor;

	@InjectMocks
	private DistrictHeatingMeasurementProvider provider;

	@Test
	void testWithEmptyParameters() {
		final var aggregateOn = MONTH;
		final var searchParams = MeasurementParameters.create();

		when(districtHeatingMonthRepositoryMock.findAllMatching(any(), any(), any(), any(), any())).thenReturn(pageMock);

		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var response = provider.getMeasurements(null, aggregateOn, null, null, searchParams);

		verify(districtHeatingMonthRepositoryMock).findAllMatching(customerOrgNrCaptor.capture(), facilityIdCaptor.capture(), fromDateCaptor.capture(), toDateCaptor.capture(), pageableCaptor.capture());

		assertThat(customerOrgNrCaptor.getValue()).isNull();
		assertThat(facilityIdCaptor.getValue()).isNull();
		assertThat(fromDateCaptor.getValue()).isNull();
		assertThat(toDateCaptor.getValue()).isNull();
		assertThat(pageableCaptor.getValue().getPageNumber()).isZero();
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(100);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(by(Sort.Direction.ASC, "measurementTimestamp"));
		assertThat(response.getMetaData().getCount()).isEqualTo(1);
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getMeasurements()).hasSize(1);
	}

	@Test
	void testWithAllParametersSet() {
		final var aggregateOn = MONTH;
		final var searchParams = MeasurementParameters.create();
		final var legalId = "legalId";
		final var partyId = "partyId";
		final var facilityId = "facilityId";
		final var fromDateTime = LocalDateTime.now().minusMonths(1);
		final var toDateTime = LocalDateTime.now();
		final var readingSequence = Integer.valueOf(22);
		final var feedType = "feedType";
		final var feedTypeId = Integer.valueOf(33);
		searchParams.setPartyId(partyId);
		searchParams.setFacilityId(facilityId);

		when(districtHeatingMonthRepositoryMock.findAllMatching(anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class))).thenReturn(pageMock);

		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(2);
		when(pageMock.getTotalElements()).thenReturn(2L);
		when(entityMock.getReadingSequence()).thenReturn(readingSequence);
		when(entityMock.getFeedType()).thenReturn(feedType);
		when(entityMock.getFacilityId()).thenReturn(facilityId);
		when(entityMock.getFeedTypeId()).thenReturn(feedTypeId);

		final var response = provider.getMeasurements(legalId, aggregateOn, fromDateTime, toDateTime, searchParams);

		verify(districtHeatingMonthRepositoryMock).findAllMatching(customerOrgNrCaptor.capture(), facilityIdCaptor.capture(), fromDateCaptor.capture(), toDateCaptor.capture(), pageableCaptor.capture());

		assertThat(customerOrgNrCaptor.getValue()).isEqualTo(legalId);
		assertThat(facilityIdCaptor.getValue()).isEqualTo(facilityId);
		assertThat(fromDateCaptor.getValue()).isEqualTo(fromDateTime);
		assertThat(toDateCaptor.getValue()).isEqualTo(toDateTime);
		assertThat(pageableCaptor.getValue().getPageNumber()).isZero();
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(100);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(by(ASC, "measurementTimestamp"));
		assertThat(response.getMetaData().getCount()).isEqualTo(1);
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(2);
		assertThat(response.getMeasurements()).hasSize(1);
		assertThat(response.getMeasurements().get(0).getMetaData()).hasSize(2);
		assertThat(response.getMeasurements().get(0).getMetaData().get(0).getValue()).isEqualTo(readingSequence.toString());
		assertThat(response.getMeasurements().get(0).getMetaData().get(1).getValue()).isEqualTo(feedTypeId.toString());
	}

	@Test
	void testForPageLargerThanResultsMaxPage() {

		when(districtHeatingMonthRepositoryMock.findAllMatching(any(), any(), any(), any(), any(Pageable.class))).thenReturn(pageMock);

		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var searchParams = MeasurementParameters.create();
		searchParams.setPage(2);
		final var response = provider.getMeasurements(null, MONTH, null, null, searchParams);

		verify(districtHeatingMonthRepositoryMock).findAllMatching(any(), any(), any(), any(), pageableCaptor.capture());

		assertThat(response.getMetaData().getCount()).isZero();
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getMeasurements()).isEmpty();

	}

	@Test
	void testProblemIsThrownWhenNotSupportedAggregation() {
		final var searchParams = MeasurementParameters.create();

		ThrowableProblem e = assertThrows(ThrowableProblem.class, () -> provider.getMeasurements(null, HOUR, null, null, searchParams));
		assertThat(e.getStatus()).isEqualTo(NOT_IMPLEMENTED);
		assertThat(e.getMessage()).isEqualTo("Not Implemented: aggregation 'HOUR' and category 'DISTRICT_HEATING'");

		verifyNoInteractions(districtHeatingMonthRepositoryMock);
	}
}
