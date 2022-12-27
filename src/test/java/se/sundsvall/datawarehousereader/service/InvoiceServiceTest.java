package se.sundsvall.datawarehousereader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.sort;
import static se.sundsvall.datawarehousereader.service.mapper.InvoiceMapper.toDetails;
import static se.sundsvall.datawarehousereader.service.mapper.InvoiceMapper.toInvoices;

import java.time.LocalDate;
import java.util.Collections;
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
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.InvoiceDetailRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.InvoiceRepository;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceDetailEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
	@Mock
	private InvoiceRepository invoiceRepositoryMock;

	@Mock
	private InvoiceDetailRepository invoiceDetailRepositoryMock;

	@Mock
	private Page<InvoiceEntity> pageMock;

	@Mock
	private InvoiceEntity entityMock;

	@InjectMocks
	private InvoiceService service;

	@Captor
	private ArgumentCaptor<InvoiceParameters> parametersCaptor;

	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;

	@Test
	void testWithEmptyParameters() {
		when(invoiceRepositoryMock.findAllByParameters(any(InvoiceParameters.class), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var response = service.getInvoices(InvoiceParameters.create());

		verify(invoiceRepositoryMock).findAllByParameters(parametersCaptor.capture(), pageableCaptor.capture());

		assertThat(parametersCaptor.getValue())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortBy", "sortDirection")
			.extracting(InvoiceParameters::getPage, InvoiceParameters::getLimit)
			.isEqualTo(List.of(1, 100));
		assertThat(pageableCaptor.getValue().getPageNumber()).isZero();
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(100);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(sort(InvoiceEntity.class).by(InvoiceEntity::getInvoiceDate));
		assertThat(response.getMetaData().getCount()).isEqualTo(1);
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getInvoices()).isEqualTo(toInvoices(List.of(entityMock)));
	}

	@Test
	void testWithAllParametersSet() {
		when(invoiceRepositoryMock.findAllByParameters(any(InvoiceParameters.class), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getContent()).thenReturn(List.of(entityMock));
		when(pageMock.getTotalPages()).thenReturn(2);
		when(pageMock.getTotalElements()).thenReturn(2L);

		final var administration = "administration";
		final var customerNumber = List.of("1337", "1338");
		final var customerType = CustomerType.PRIVATE;
		final var dueDateFrom = LocalDate.now().minusDays(30);
		final var dueDateTo = LocalDate.now().minusDays(20);
		final var facilityId = List.of("facilityId1", "facilityId2");
		final var invoiceDateFrom = LocalDate.now().minusDays(10);
		final var invoiceDateTo = LocalDate.now();
		final var invoiceName = "invoiceName";
		final var invoiceNumber = 4321L;
		final var invoiceStatus = "invoiceStatus";
		final var invoiceType = "invoiceType";
		final var limit = 1;
		final var ocrNumber = 1234L;
		final var organizationGroup = "organizationGroup";
		final var page = 2;
		final var params = InvoiceParameters.create();
		params.setAdministration(administration);
		params.setCustomerNumber(customerNumber);
		params.setCustomerType(customerType);
		params.setDueDateFrom(dueDateFrom);
		params.setDueDateTo(dueDateTo);
		params.setFacilityId(facilityId);
		params.setInvoiceDateFrom(invoiceDateFrom);
		params.setInvoiceDateTo(invoiceDateTo);
		params.setInvoiceName(invoiceName);
		params.setInvoiceNumber(invoiceNumber);
		params.setInvoiceStatus(invoiceStatus);
		params.setInvoiceType(invoiceType);
		params.setLimit(limit);
		params.setOcrNumber(ocrNumber);
		params.setOrganizationGroup(organizationGroup);
		params.setPage(page);

		final var response = service.getInvoices(params);
		verify(invoiceRepositoryMock).findAllByParameters(parametersCaptor.capture(), pageableCaptor.capture());

		assertThat(parametersCaptor.getValue()).usingRecursiveComparison().isEqualTo(params);
		assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(page - 1);
		assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(limit);
		assertThat(pageableCaptor.getValue().getSort()).isEqualTo(sort(InvoiceEntity.class).by(InvoiceEntity::getInvoiceDate));
		assertThat(response.getMetaData().getCount()).isEqualTo(1);
		assertThat(response.getMetaData().getLimit()).isEqualTo(1);
		assertThat(response.getMetaData().getPage()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(2);
		assertThat(response.getInvoices()).isEqualTo(toInvoices(List.of(entityMock)));
	}

	@Test
	void testForPageLargerThanResultsMaxPage() {
		when(invoiceRepositoryMock.findAllByParameters(any(InvoiceParameters.class), any(Pageable.class))).thenReturn(pageMock);
		when(pageMock.getTotalPages()).thenReturn(1);
		when(pageMock.getTotalElements()).thenReturn(1L);

		final var params = InvoiceParameters.create();
		params.setPage(2);
		final var response = service.getInvoices(params);

		verify(invoiceRepositoryMock).findAllByParameters(any(InvoiceParameters.class), any(Pageable.class));

		assertThat(response.getMetaData().getCount()).isZero();
		assertThat(response.getMetaData().getLimit()).isEqualTo(100);
		assertThat(response.getMetaData().getPage()).isEqualTo(2);
		assertThat(response.getMetaData().getTotalPages()).isEqualTo(1);
		assertThat(response.getMetaData().getTotalRecords()).isEqualTo(1);
		assertThat(response.getInvoices()).isEmpty();

	}

	@Test
	void testDeprecatedInvoiceDetails() {
		final var invoiceNumber = 1337;
		final var detailEntities = List.of(new InvoiceDetailEntity());

		when(invoiceDetailRepositoryMock.findAllByInvoiceNumber(anyLong())).thenReturn(detailEntities);

		final var response = service.getInvoiceDetails(invoiceNumber);

		verify(invoiceDetailRepositoryMock).findAllByInvoiceNumber(invoiceNumber);
		assertThat(response).isEqualTo(toDetails(detailEntities));
	}

	@Test
	void testDeprecatedInvoiceDetailsNotFound() {
		when(invoiceDetailRepositoryMock.findAllByInvoiceNumber(anyLong())).thenReturn(Collections.emptyList());

		final var invoiceNumber = 1337L;

		final var exception = assertThrows(ThrowableProblem.class, () -> service.getInvoiceDetails(invoiceNumber));

		assertThat(exception.getStatus()).isNotNull();
		assertThat(exception.getStatus().getStatusCode()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(exception.getStatus().getReasonPhrase()).isEqualTo(Status.NOT_FOUND.getReasonPhrase());
		assertThat(exception.getMessage()).isEqualTo("Not Found: No invoicedetails found for invoicenumber '1337'");
		assertThat(exception.getDetail()).isEqualTo("No invoicedetails found for invoicenumber '1337'");
	}

	@Test
	void testInvoiceDetails() {
		final var organizationNumber = "1234567890";
		final var invoiceNumber = 1337;
		final var detailEntities = List.of(new InvoiceDetailEntity());

		when(invoiceDetailRepositoryMock.findAllByOrganizationIdAndInvoiceNumber(any(), anyLong())).thenReturn(detailEntities);

		final var response = service.getInvoiceDetails(organizationNumber, invoiceNumber);

		verify(invoiceDetailRepositoryMock).findAllByOrganizationIdAndInvoiceNumber(organizationNumber, invoiceNumber);
		assertThat(response).isEqualTo(toDetails(detailEntities));
	}

	@Test
	void testInvoiceDetailsNotFound() {
		when(invoiceDetailRepositoryMock.findAllByOrganizationIdAndInvoiceNumber(any(), anyLong())).thenReturn(Collections.emptyList());

		final var organizationNumber = "1234567890";
		final var invoiceNumber = 1337L;

		final var exception = assertThrows(ThrowableProblem.class, () -> service.getInvoiceDetails(organizationNumber, invoiceNumber));

		assertThat(exception.getStatus()).isNotNull();
		assertThat(exception.getStatus().getStatusCode()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(exception.getStatus().getReasonPhrase()).isEqualTo(Status.NOT_FOUND.getReasonPhrase());
		assertThat(exception.getMessage()).isEqualTo("Not Found: No invoicedetails found for invoice issuer '1234567890' and invoicenumber '1337'");
		assertThat(exception.getDetail()).isEqualTo("No invoicedetails found for invoice issuer '1234567890' and invoicenumber '1337'");
	}
}
