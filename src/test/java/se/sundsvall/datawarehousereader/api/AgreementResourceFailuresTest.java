package se.sundsvall.datawarehousereader.api;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.datawarehousereader.service.AgreementService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class AgreementResourceFailuresTest {

	@MockBean
	private AgreementService serviceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getAgreementsPageLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("page", 0)
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("page")
			.jsonPath("$.violations[0].message").isEqualTo("must be greater than or equal to 1");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getAgreementsLimitLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("limit", 0)
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("limit")
			.jsonPath("$.violations[0].message").isEqualTo("must be greater than or equal to 1");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getCustomerEngagementsLimitMoreThanMaximum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("limit", 1001)
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("limit")
			.jsonPath("$.violations[0].message").isEqualTo("must be less than or equal to 1000");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getAgreementsPartyIdNotValidUUID() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("partyId", "not-valid-uuid")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("partyId")
			.jsonPath("$.violations[0].message").isEqualTo("not a valid UUID");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getAgreementsNoValidCategory() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("category", "not-valid-category")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("category")
			.jsonPath("$.violations[0].message").isEqualTo("""
				Failed to convert property value of type 'java.lang.String' to required type 'java.util.List' for property 'category'; \
				nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] \
				to type [@io.swagger.v3.oas.annotations.media.ArraySchema se.sundsvall.datawarehousereader.api.model.Category] for value 'not-valid-category'; \
				nested exception is about:blank{400, Bad Request, Invalid value for enum Category: not-valid-category}""");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getAgreementsNoValidSortBy() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/agreements")
			.queryParam("sortBy", "not-valid-property")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("agreementParameters")
			.jsonPath("$.violations[0].message").isEqualTo("""
				One or more of the sortBy members [not-valid-property] are not valid. Valid properties to sort by are [agreementId, billingId, uuid, \
				customerOrgId, customerId, facilityId, category, description, mainAgreement, binding, bindingRule, fromDate, toDate].""");

		verifyNoInteractions(serviceMock);
	}
}
