package se.sundsvall.datawarehousereader.api;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;

import java.time.LocalDate;
import java.util.UUID;

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
import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementResponse;
import se.sundsvall.datawarehousereader.service.AgreementService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class AgreementResourceTest {

	private static final int PAGE = 12;
	private static final int LIMIT = 50;
	private static final int DEFAULT_LIMIT = 100;
	private static final int DEFAULT_PAGE = 1;
	private static final String AGREEMENT_ID = "agreementId";
	private static final String BILLING_ID = "billingId";
	private static final String CUSTOMER_NUMBER = "customerNumber";
	private static final String PARTY_ID = UUID.randomUUID().toString();
	private static final Category CATEGORY = ELECTRICITY;
	private static final String FACILITY_ID = "facilityId";
	private static final String DESCRIPTION = "description";
	private static final Boolean MAIN_AGREEMENT = true;
	private static final Boolean BINDING = false;
	private static final String BINDING_RULE = "bindingRule";
	private static final LocalDate FROM_DATE = LocalDate.now().minusMonths(10L);
	private static final LocalDate TO_DATE = LocalDate.now();

	@MockBean
	private AgreementService serviceMock;

	@Captor
	private ArgumentCaptor<AgreementParameters> parametersCaptor;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getAgreementsAllParams() {
		when(serviceMock.getAgreements(any())).thenReturn(AgreementResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParams(createParameterMap(PAGE, LIMIT, AGREEMENT_ID, BILLING_ID, CUSTOMER_NUMBER, PARTY_ID, CATEGORY, FACILITY_ID, DESCRIPTION, MAIN_AGREEMENT, BINDING, BINDING_RULE, FROM_DATE, TO_DATE))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(AgreementResponse.class)
			.isEqualTo(AgreementResponse.create());

		verify(serviceMock).getAgreements(parametersCaptor.capture());
		AgreementParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCustomerNumber()).isEqualTo(CUSTOMER_NUMBER);
		assertThat(parameters.getPartyId()).isEqualTo(PARTY_ID);
		assertThat(parameters.getLimit()).isEqualTo(LIMIT);
		assertThat(parameters.getBillingId()).isEqualTo(BILLING_ID);
		assertThat(parameters.getAgreementId()).isEqualTo(AGREEMENT_ID);
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(parameters.getMainAgreement()).isEqualTo(MAIN_AGREEMENT);
		assertThat(parameters.getBinding()).isEqualTo(BINDING);
		assertThat(parameters.getBindingRule()).isEqualTo(BINDING_RULE);
		assertThat(parameters.getCategory()).containsExactly(CATEGORY);
		assertThat(parameters.getFromDate()).isEqualTo(FROM_DATE);
		assertThat(parameters.getToDate()).isEqualTo(TO_DATE);
		assertThat(parameters.getPage()).isEqualTo(PAGE);
	}

	@Test
	void getAgreementsDefaultValues() {

		when(serviceMock.getAgreements(any())).thenReturn(AgreementResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParams(createParameterMap(null, null, null, null, null, null, CATEGORY, FACILITY_ID, null, null, null, null, null, null))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(AgreementResponse.class)
			.isEqualTo(AgreementResponse.create());

		verify(serviceMock).getAgreements(parametersCaptor.capture());
		AgreementParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCustomerNumber()).isNull();
		assertThat(parameters.getPartyId()).isNull();
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getBillingId()).isNull();
		assertThat(parameters.getAgreementId()).isNull();
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getDescription()).isNull();
		assertThat(parameters.getMainAgreement()).isNull();
		assertThat(parameters.getBinding()).isNull();
		assertThat(parameters.getBindingRule()).isNull();
		assertThat(parameters.getCategory()).containsExactly(CATEGORY);
		assertThat(parameters.getFromDate()).isNull();
		assertThat(parameters.getToDate()).isNull();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
	}

	@Test
	void getAgreementsForMultipleCategories() {
		final var inParams = createParameterMap(null, null, null, null, null, null, CATEGORY, FACILITY_ID, null, null, null, null, null, null);
		inParams.add("category", Category.WASTE_MANAGEMENT.toString());

		when(serviceMock.getAgreements(any())).thenReturn(AgreementResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParams(inParams)
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(AgreementResponse.class)
			.isEqualTo(AgreementResponse.create());

		verify(serviceMock).getAgreements(parametersCaptor.capture());
		AgreementParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCustomerNumber()).isNull();
		assertThat(parameters.getPartyId()).isNull();
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getBillingId()).isNull();
		assertThat(parameters.getAgreementId()).isNull();
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getDescription()).isNull();
		assertThat(parameters.getMainAgreement()).isNull();
		assertThat(parameters.getBinding()).isNull();
		assertThat(parameters.getBindingRule()).isNull();
		assertThat(parameters.getCategory()).containsExactlyInAnyOrder(CATEGORY, Category.WASTE_MANAGEMENT);
		assertThat(parameters.getFromDate()).isNull();
		assertThat(parameters.getToDate()).isNull();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);

	}

	@Test
	void getAgreementsNoParams() {
		when(serviceMock.getAgreements(any())).thenReturn(AgreementResponse.create());

		webTestClient.get()
			.uri("/agreements")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(AgreementResponse.class)
			.isEqualTo(AgreementResponse.create());
	}

	private MultiValueMap<String, String> createParameterMap(Integer page, Integer limit, String agreementId, String billingId, String customerNumber, String partyId, Category category, String facilityId, String description, Boolean mainAgreement,
		Boolean binding, String bindingRule, LocalDate fromDate, LocalDate toDate) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		ofNullable(page).ifPresent(p -> parameters.add("page", p.toString()));
		ofNullable(limit).ifPresent(p -> parameters.add("limit", p.toString()));
		ofNullable(agreementId).ifPresent(p -> parameters.add("agreementId", p));
		ofNullable(billingId).ifPresent(p -> parameters.add("billingId", p));
		ofNullable(customerNumber).ifPresent(p -> parameters.add("customerNumber", p));
		ofNullable(partyId).ifPresent(p -> parameters.add("partyId", p));
		ofNullable(category).ifPresent(p -> parameters.add("category", p.name()));
		ofNullable(facilityId).ifPresent(p -> parameters.add("facilityId", p));
		ofNullable(description).ifPresent(p -> parameters.add("description", p));
		ofNullable(mainAgreement).ifPresent(p -> parameters.add("mainAgreement", p.toString()));
		ofNullable(binding).ifPresent(p -> parameters.add("binding", p.toString()));
		ofNullable(bindingRule).ifPresent(p -> parameters.add("bindingRule", p));
		ofNullable(fromDate).ifPresent(p -> parameters.add("fromDate", p.toString()));
		ofNullable(toDate).ifPresent(p -> parameters.add("toDate", p.toString()));

		return parameters;
	}
}
