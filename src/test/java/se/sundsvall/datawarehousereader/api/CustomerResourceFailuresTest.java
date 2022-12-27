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
import se.sundsvall.datawarehousereader.service.CustomerService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class CustomerResourceFailuresTest {

	@MockBean
	private CustomerService serviceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getCustomerEngagementsPageLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/customer/engagements")
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
	void getCustomerEngagementsLimitLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/customer/engagements")
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
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/customer/engagements")
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
	void getCustomerEngagementsPartyIdNotValidUUID() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/customer/engagements")
			.queryParam("partyId", "not-valid-uuid")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("partyId[0]")
			.jsonPath("$.violations[0].message").isEqualTo("not a valid UUID");

		verifyNoInteractions(serviceMock);
	}

	@Test
	void getCustomerEngagementsNoValidSortBy() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/customer/engagements")
			.queryParam("sortBy", "not-valid-property")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("customerEngagementParameters")
			.jsonPath("$.violations[0].message").isEqualTo("""
				One or more of the sortBy members [not-valid-property] are not valid. Valid properties to sort by are \
				[customerId, organizationId, customerOrgId, customerType, organizationName].""");

		verifyNoInteractions(serviceMock);
	}
}
