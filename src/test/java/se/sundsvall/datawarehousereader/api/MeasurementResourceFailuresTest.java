package se.sundsvall.datawarehousereader.api;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.service.MeasurementService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class MeasurementResourceFailuresTest {

	private static final String PARTY_ID = UUID.randomUUID().toString();
	private static final String FACILITY_ID = "facilityId";
	private static final Category CATEGORY = Category.DISTRICT_HEATING;
	private static final Aggregation AGGREGATION = Aggregation.DAY;
	private static final String FROM_DATE_TIME = "2022-05-01T00:00:00+01:00";
	private static final String TO_DATE_TIME = "2022-05-31T23:59:59+01:00";

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private MeasurementService serviceMock;

	private UriBuilder setupDefaultParams(UriBuilder uriBuilder) {
		uriBuilder.queryParam("partyId", PARTY_ID);
		uriBuilder.queryParam("facilityId", FACILITY_ID);
		uriBuilder.queryParam("fromDateTime", "{FROM_DATE_TIME}");
		uriBuilder.queryParam("toDateTime", "{TO_DATE_TIME}");

		return uriBuilder;
	}

	@Test
	void getMeasurementsPageLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.queryParam("page", 0)
			.build(CATEGORY, AGGREGATION, FROM_DATE_TIME, TO_DATE_TIME))
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
	void getMeasurementsLimitLessThanMinimum() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.queryParam("limit", 0)
			.build(CATEGORY, AGGREGATION, FROM_DATE_TIME, TO_DATE_TIME))
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
	void getMeasurementsLimitMoreThanMaximum() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.queryParam("limit", 1001)
			.build(CATEGORY, AGGREGATION, FROM_DATE_TIME, TO_DATE_TIME))
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
	void getMeasurementsPartyIdNotValidUUID() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.replaceQueryParam("partyId", "not-valid-uuid")
			.build(CATEGORY, AGGREGATION, FROM_DATE_TIME, TO_DATE_TIME))
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
	void getMeasurementsNotValidCategory() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.build("not-valid-category", AGGREGATION, FROM_DATE_TIME, TO_DATE_TIME))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Bad Request")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.detail").isEqualTo("Failed to convert value of type 'java.lang.String' to required type 'se.sundsvall.datawarehousereader.api.model.Category'; "
				+ "nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type "
				+ "[@io.swagger.v3.oas.annotations.Parameter @org.springframework.web.bind.annotation.PathVariable se.sundsvall.datawarehousereader.api.model.Category] "
				+ "for value 'not-valid-category'; nested exception is about:blank{400, Bad Request, Invalid value for enum Category: not-valid-category}");
	}

	@Test
	void getMeasurementsNotValidAggregation() {
		webTestClient.get().uri(uriBuilder -> setupDefaultParams(uriBuilder.path("/measurements/{category}/{aggregateOn}"))
			.build(CATEGORY, "not-valid-aggregation", FROM_DATE_TIME, TO_DATE_TIME))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Bad Request")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.detail").isEqualTo("Failed to convert value of type 'java.lang.String' to required type 'se.sundsvall.datawarehousereader.api.model.measurement.Aggregation'; "
				+ "nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type "
				+ "[@io.swagger.v3.oas.annotations.Parameter @org.springframework.web.bind.annotation.PathVariable se.sundsvall.datawarehousereader.api.model.measurement.Aggregation] "
				+ "for value 'not-valid-aggregation'; nested exception is about:blank{400, Bad Request, Invalid value for enum Aggregation: not-valid-aggregation}");
	}

	@Test
	void getInvoicesNoValidSortBy() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/measurements/{category}/{aggregateOn}")
			.queryParam("sortBy", "not-valid-property")
			.build(CATEGORY, AGGREGATION))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("measurementParameters")
			.jsonPath("$.violations[0].message").isEqualTo("""
				One or more of the sortBy members [not-valid-property] are not valid. Valid properties to sort by are \
				[customerOrgId, uuid, facilityId, feedType, interpolation, measurementTimestamp, unit, usage].""");

		verifyNoInteractions(serviceMock);
	}
}
