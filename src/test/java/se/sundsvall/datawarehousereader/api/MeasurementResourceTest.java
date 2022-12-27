package se.sundsvall.datawarehousereader.api;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementResponse;
import se.sundsvall.datawarehousereader.service.MeasurementService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class MeasurementResourceTest {

	private static final Category CATEGORY = Category.DISTRICT_HEATING;
	private static final Aggregation AGGREGATION = Aggregation.MONTH;
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LIMIT = 100;
	private static final int PAGE = 3;
	private static final int LIMIT = 17;
	private static final String PARTY_ID = "81471222-5798-11e9-ae24-57fa13b361e1";
	private static final String FACILITY_ID = "facilityId";
	private static final OffsetDateTime FROM_DATE_TIME = OffsetDateTime.now().minusMonths(1);
	private static final OffsetDateTime TO_DATE_TIME = OffsetDateTime.now();

	@MockBean
	private MeasurementService serviceMock;

	@Captor
	private ArgumentCaptor<MeasurementParameters> parametersCaptor;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getMeasurementsWithNoParameters() {
		when(serviceMock.getMeasurements(any(), any(), any())).thenReturn(MeasurementResponse.create());

		webTestClient.get().uri("/measurements/{category}/{aggregateOn}", CATEGORY, AGGREGATION)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON_VALUE)
			.expectBody().json("{}");

		verify(serviceMock).getMeasurements(eq(CATEGORY), eq(AGGREGATION), parametersCaptor.capture());
		MeasurementParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getPartyId()).isNull();
		assertThat(parameters.getFacilityId()).isNull();
		assertThat(parameters.getFromDateTime()).isNull();
		assertThat(parameters.getToDateTime()).isNull();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
	}

	@Test
	void getMeasurementsWithCustomCapitalizationOnCategoryAndAggregation() {
		when(serviceMock.getMeasurements(any(), any(), any())).thenReturn(MeasurementResponse.create());

		final var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/measurements/{category}/{aggregateOn}")
			.queryParams(createParameterMap(PAGE, LIMIT, PARTY_ID, FACILITY_ID, FROM_DATE_TIME, TO_DATE_TIME))
			.build(CATEGORY.name().toLowerCase(), AGGREGATION.name().toLowerCase()))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON_VALUE)
			.expectBody(MeasurementResponse.class)
			.returnResult()
			.getResponseBody();

		assertThat(response).isNotNull().isEqualTo(MeasurementResponse.create());
		verify(serviceMock).getMeasurements(eq(CATEGORY), eq(AGGREGATION), parametersCaptor.capture());
		MeasurementParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getPage()).isEqualTo(PAGE);
		assertThat(parameters.getLimit()).isEqualTo(LIMIT);
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getPartyId()).isEqualTo(PARTY_ID);
		assertThat(parameters.getFromDateTime()).isEqualTo(FROM_DATE_TIME);
		assertThat(parameters.getToDateTime()).isEqualTo(TO_DATE_TIME);
	}

	private MultiValueMap<String, String> createParameterMap(Integer page, Integer limit, String partyId, String facilityId, OffsetDateTime fromDateTime,
		OffsetDateTime toDateTime) {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		ofNullable(page).ifPresent(p -> parameters.add("page", valueOf(p)));
		ofNullable(limit).ifPresent(p -> parameters.add("limit", valueOf(p)));
		ofNullable(partyId).ifPresent(p -> parameters.add("partyId", p));
		ofNullable(facilityId).ifPresent(p -> parameters.add("facilityId", p));
		ofNullable(fromDateTime).ifPresent(p -> parameters.add("fromDateTime", p.format(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()))));
		ofNullable(toDateTime).ifPresent(p -> parameters.add("toDateTime", p.format(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()))));

		return parameters;
	}
}
