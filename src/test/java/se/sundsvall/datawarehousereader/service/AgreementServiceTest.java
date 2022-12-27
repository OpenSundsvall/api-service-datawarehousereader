package se.sundsvall.datawarehousereader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.unsorted;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.AgreementRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;
import se.sundsvall.datawarehousereader.service.logic.PartyProvider;

@ExtendWith(MockitoExtension.class)
class AgreementServiceTest {
	@Mock
	private AgreementRepository repositoryMock;

	@Mock
	private PartyProvider partyProviderMock;

	@Mock
	private Page<AgreementEntity> pageMock;

	@Mock
	private AgreementEntity entityMock;

	@InjectMocks
	private AgreementService service;

	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;

	@Test
	void testWithEmptyParameters() {
		when(repositoryMock.findAllByParameters(ArgumentMatchers.any(), isNull(), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var response = service.getAgreements(AgreementParameters.create());

		verify(partyProviderMock, never()).translateToLegalId("partyId");
		verify(repositoryMock).findAllByParameters(eq(AgreementParameters.create()), isNull(), pageableCaptor.capture());

		assertThat(pageableCaptor.getValue().getPageNumber()).isZero();
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(100);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(unsorted());
		assertThat(response.getMetaData().getCount()).isEqualTo(1L);
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getAgreements()).hasSize(1);
	}

	@Test
	void testWithAllParametersSet() {
		final var limit = 1;
		final var page = 2;
		final var params = AgreementParameters.create()
			.withCustomerNumber("customerNumber")
			.withPartyId("partyId")
			.withBillingId("billingId")
			.withAgreementId("agreementId")
			.withFacilityId("facilityId")
			.withDescription("description")
			.withCategory(List.of(Category.ELECTRICITY))
			.withMainAgreement(true)
			.withBinding(true)
			.withFromDate(LocalDate.now().minusMonths(2L))
			.withToDate(LocalDate.now());
		params.setLimit(limit);
		params.setPage(page);

		when(partyProviderMock.translateToLegalId("partyId")).thenReturn("legalId");
		when(repositoryMock.findAllByParameters(ArgumentMatchers.any(), eq("legalId"), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(2);
		when(pageMock.getTotalElements()).thenReturn(2L);

		final var response = service.getAgreements(params);

		verify(partyProviderMock).translateToLegalId("partyId");
		verify(repositoryMock).findAllByParameters(eq(params), eq("legalId"), pageableCaptor.capture());

		assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(page - 1);
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(limit);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(unsorted());

		assertThat(response.getMetaData().getCount()).isEqualTo(1L);
		assertThat(response.getMetaData().getLimit()).isEqualTo(limit);
		assertThat(response.getMetaData().getPage()).isEqualTo(page);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(2);
		assertThat(response.getAgreements()).hasSize(1);
	}

	@Test
	void testForPageLargerThanResultsMaxPage() {
		when(repositoryMock.findAllByParameters(any(), any(), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var params = AgreementParameters.create();
		params.setPage(2);

		final var response = service.getAgreements(params);

		verify(partyProviderMock, never()).translateToLegalId("partyId");
		verify(repositoryMock).findAllByParameters(eq(params), isNull(), pageableCaptor.capture());

		assertThat(response.getMetaData().getCount()).isZero();
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getAgreements()).isEmpty();
	}
}
