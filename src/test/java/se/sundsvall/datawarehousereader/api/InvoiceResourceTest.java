package se.sundsvall.datawarehousereader.api;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceDetail;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceResponse;
import se.sundsvall.datawarehousereader.service.InvoiceService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class InvoiceResourceTest {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LIMIT = 100;
	private static final int PAGE = 3;
	private static final int LIMIT = 17;
	private static final String CUSTOMER_NUMBER = "321";
	private static final CustomerType CUSTOMER_TYPE = CustomerType.ENTERPRISE;
	private static final String FACILITY_ID = "facilityId";
	private static final long INVOICE_NUMBER = 333L;
	private static final LocalDate INVOICE_DATE_FROM = LocalDate.now().minusYears(2);
	private static final LocalDate INVOICE_DATE_TO = LocalDate.now().minusYears(1);
	private static final String INVOICE_NAME = "invoiceName";
	private static final String INVOICE_TYPE = "invoiceType";
	private static final String INVOICE_STATUS = "invoiceStatus";
	private static final long OCR_NUMBER = 444L;
	private static final LocalDate DUE_DATE_FROM = LocalDate.now().minusMonths(1);
	private static final LocalDate DUE_DATE_TO = LocalDate.now();
	private static final String ORGANIZATION_GROUP = "organizationGroup";
	private static final String ORGANIZATION_NUMBER = "5565027223";
	private static final String ADMINISTRATION = "administration";

	@MockBean
	private InvoiceService serviceMock;

	@Captor
	private ArgumentCaptor<InvoiceParameters> parametersCaptor;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInvoicesAllParams() {
		when(serviceMock.getInvoices(any())).thenReturn(InvoiceResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/invoices")
			.queryParams(createParameterMap(PAGE, LIMIT, CUSTOMER_NUMBER, CUSTOMER_TYPE.getStadsbackenTranslation(), FACILITY_ID, INVOICE_NUMBER, INVOICE_DATE_FROM, INVOICE_DATE_TO, INVOICE_NAME, INVOICE_TYPE,
				INVOICE_STATUS, OCR_NUMBER, DUE_DATE_FROM, DUE_DATE_TO, ORGANIZATION_GROUP, ADMINISTRATION))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoiceResponse.class)
			.isEqualTo(InvoiceResponse.create());

		verify(serviceMock).getInvoices(parametersCaptor.capture());
		InvoiceParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getAdministration()).isEqualTo(ADMINISTRATION);
		assertThat(parameters.getCustomerNumber()).isEqualTo(List.of(CUSTOMER_NUMBER));
		assertThat(parameters.getCustomerType()).isEqualTo(CUSTOMER_TYPE);
		assertThat(parameters.getDueDateFrom()).isEqualTo(DUE_DATE_FROM);
		assertThat(parameters.getDueDateTo()).isEqualTo(DUE_DATE_TO);
		assertThat(parameters.getFacilityId()).isEqualTo(List.of(FACILITY_ID));
		assertThat(parameters.getInvoiceDateFrom()).isEqualTo(INVOICE_DATE_FROM);
		assertThat(parameters.getInvoiceDateTo()).isEqualTo(INVOICE_DATE_TO);
		assertThat(parameters.getInvoiceName()).isEqualTo(INVOICE_NAME);
		assertThat(parameters.getInvoiceNumber()).isEqualTo(INVOICE_NUMBER);
		assertThat(parameters.getInvoiceStatus()).isEqualTo(INVOICE_STATUS);
		assertThat(parameters.getInvoiceType()).isEqualTo(INVOICE_TYPE);
		assertThat(parameters.getLimit()).isEqualTo(LIMIT);
		assertThat(parameters.getOcrNumber()).isEqualTo(OCR_NUMBER);
		assertThat(parameters.getOrganizationGroup()).isEqualTo(ORGANIZATION_GROUP);
		assertThat(parameters.getPage()).isEqualTo(PAGE);
	}

	@Test
	void getInvoicesDefaultValues() {
		when(serviceMock.getInvoices(any())).thenReturn(InvoiceResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/invoices")
			.queryParams(createParameterMap(null, null, CUSTOMER_NUMBER, CUSTOMER_TYPE.getStadsbackenTranslation(), FACILITY_ID, INVOICE_NUMBER, INVOICE_DATE_FROM, INVOICE_DATE_TO, INVOICE_NAME, INVOICE_TYPE,
				INVOICE_STATUS, OCR_NUMBER, DUE_DATE_FROM, DUE_DATE_TO, ORGANIZATION_GROUP, ADMINISTRATION))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoiceResponse.class)
			.isEqualTo(InvoiceResponse.create());

		verify(serviceMock).getInvoices(parametersCaptor.capture());
		InvoiceParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getAdministration()).isEqualTo(ADMINISTRATION);
		assertThat(parameters.getCustomerNumber()).isEqualTo(List.of(CUSTOMER_NUMBER));
		assertThat(parameters.getCustomerType()).isEqualTo(CUSTOMER_TYPE);
		assertThat(parameters.getDueDateFrom()).isEqualTo(DUE_DATE_FROM);
		assertThat(parameters.getDueDateTo()).isEqualTo(DUE_DATE_TO);
		assertThat(parameters.getFacilityId()).isEqualTo(List.of(FACILITY_ID));
		assertThat(parameters.getInvoiceDateFrom()).isEqualTo(INVOICE_DATE_FROM);
		assertThat(parameters.getInvoiceDateTo()).isEqualTo(INVOICE_DATE_TO);
		assertThat(parameters.getInvoiceName()).isEqualTo(INVOICE_NAME);
		assertThat(parameters.getInvoiceNumber()).isEqualTo(INVOICE_NUMBER);
		assertThat(parameters.getInvoiceStatus()).isEqualTo(INVOICE_STATUS);
		assertThat(parameters.getInvoiceType()).isEqualTo(INVOICE_TYPE);
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getOcrNumber()).isEqualTo(OCR_NUMBER);
		assertThat(parameters.getOrganizationGroup()).isEqualTo(ORGANIZATION_GROUP);
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
	}

	@Test
	void getInvoicesNoParams() {
		when(serviceMock.getInvoices(any())).thenReturn(InvoiceResponse.create());

		webTestClient.get().uri("/invoices")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoiceResponse.class)
			.isEqualTo(InvoiceResponse.create());

		verify(serviceMock).getInvoices(parametersCaptor.capture());
		InvoiceParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getAdministration()).isNull();
		assertThat(parameters.getCustomerNumber()).isNull();
		assertThat(parameters.getCustomerType()).isNull();
		assertThat(parameters.getDueDateFrom()).isNull();
		assertThat(parameters.getDueDateTo()).isNull();
		assertThat(parameters.getFacilityId()).isNull();
		assertThat(parameters.getInvoiceDateFrom()).isNull();
		assertThat(parameters.getInvoiceDateTo()).isNull();
		assertThat(parameters.getInvoiceName()).isNull();
		assertThat(parameters.getInvoiceNumber()).isNull();
		assertThat(parameters.getInvoiceStatus()).isNull();
		assertThat(parameters.getInvoiceType()).isNull();
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getOcrNumber()).isNull();
		assertThat(parameters.getOrganizationGroup()).isNull();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
	}

	@Test
	void getDeprecatedInvoicesDetails() {
		when(serviceMock.getInvoiceDetails(INVOICE_NUMBER)).thenReturn(List.of(InvoiceDetail.create()));

		webTestClient.get().uri("/invoices/{invoiceNumber}/details", INVOICE_NUMBER)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList(InvoiceDetail.class)
			.isEqualTo(List.of(InvoiceDetail.create()));

		verify(serviceMock).getInvoiceDetails(INVOICE_NUMBER);
	}

	@Test
	void getInvoicesDetails() {
		when(serviceMock.getInvoiceDetails(ORGANIZATION_NUMBER, INVOICE_NUMBER)).thenReturn(List.of(InvoiceDetail.create()));

		webTestClient.get().uri("/invoices/{organizationNumber}/{invoiceNumber}/details", ORGANIZATION_NUMBER, INVOICE_NUMBER)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList(InvoiceDetail.class)
			.isEqualTo(List.of(InvoiceDetail.create()));

		verify(serviceMock).getInvoiceDetails(ORGANIZATION_NUMBER, INVOICE_NUMBER);
	}

	private MultiValueMap<String, String> createParameterMap(Integer page, Integer limit, String customerNumber, String customerType, String facilityId, Long invoiceNumber, LocalDate invoiceDateFrom,
		LocalDate invoiceDateTo, String invoiceName, String invoiceType, String invoiceStatus, Long ocrNumber, LocalDate dueDateFrom, LocalDate dueDateTo, String organizationGroup,
		String administration) {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		ofNullable(page).ifPresent(p -> parameters.add("page", valueOf(p)));
		ofNullable(limit).ifPresent(p -> parameters.add("limit", valueOf(p)));
		ofNullable(customerNumber).ifPresent(p -> parameters.add("customerNumber", p));
		ofNullable(customerType).ifPresent(p -> parameters.add("customerType", p));
		ofNullable(facilityId).ifPresent(p -> parameters.add("facilityId", p));
		ofNullable(invoiceNumber).ifPresent(p -> parameters.add("invoiceNumber", valueOf(p)));
		ofNullable(invoiceDateFrom).ifPresent(p -> parameters.add("invoiceDateFrom", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(invoiceDateTo).ifPresent(p -> parameters.add("invoiceDateTo", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(invoiceName).ifPresent(p -> parameters.add("invoiceName", p));
		ofNullable(invoiceType).ifPresent(p -> parameters.add("invoiceType", p));
		ofNullable(invoiceStatus).ifPresent(p -> parameters.add("invoiceStatus", p));
		ofNullable(ocrNumber).ifPresent(p -> parameters.add("ocrNumber", valueOf(p)));
		ofNullable(dueDateFrom).ifPresent(p -> parameters.add("dueDateFrom", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(dueDateTo).ifPresent(p -> parameters.add("dueDateTo", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(organizationGroup).ifPresent(p -> parameters.add("organizationGroup", p));
		ofNullable(administration).ifPresent(p -> parameters.add("administration", p));

		return parameters;
	}
}