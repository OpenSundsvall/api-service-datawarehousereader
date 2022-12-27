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
import se.sundsvall.datawarehousereader.service.InstalledBaseService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class InstalledBaseResourceFailuresTest {

	@MockBean
	private InstalledBaseService serviceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInstalledBasePageLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
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
	void getInstalledBaseLimitLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
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
	void getInstalledBaseLimitMoreThanMaximum() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
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
	void getInstalledBaseNoValidSortBy() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
			.queryParam("sortBy", "not-valid-property")
			.build())
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("installedBaseParameters")
			.jsonPath("$.violations[0].message").isEqualTo("""
				One or more of the sortBy members [not-valid-property] are not valid. Valid properties to sort by are \
				[internalId, customerId, company, type, careOf, street, facilityId, postCode, city, houseName, dateFrom, dateTo].""");

		verifyNoInteractions(serviceMock);
	}
}
