package se.sundsvall.datawarehousereader.api;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

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
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseResponse;
import se.sundsvall.datawarehousereader.service.InstalledBaseService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class InstalledBaseResourceTest {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LIMIT = 100;
	private static final int PAGE = 6;
	private static final int LIMIT = 35;
	private static final String COMPANY = "company";
	private static final String CUSTOMER_NUMBER = "123";
	private static final String TYPE = "type";
	private static final String CARE_OF = "careOf";
	private static final String STREET = "street";
	private static final String FACILITY_ID = "facilityId";
	private static final String POST_CODE = "postCode";
	private static final String CITY = "city";

	@MockBean
	private InstalledBaseService serviceMock;

	@Captor
	private ArgumentCaptor<InstalledBaseParameters> parametersCaptor;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInstalledBaseAllParams() {
		when(serviceMock.getInstalledBase(any())).thenReturn(InstalledBaseResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
			.queryParams(createParameterMap(PAGE, LIMIT, COMPANY, CUSTOMER_NUMBER, TYPE, CARE_OF, STREET, FACILITY_ID, POST_CODE, CITY))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InstalledBaseResponse.class)
			.isEqualTo(InstalledBaseResponse.create());

		verify(serviceMock).getInstalledBase(parametersCaptor.capture());
		InstalledBaseParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCareOf()).isEqualTo(CARE_OF);
		assertThat(parameters.getCity()).isEqualTo(CITY);
		assertThat(parameters.getCompany()).isEqualTo(COMPANY);
		assertThat(parameters.getCustomerNumber()).isEqualTo(CUSTOMER_NUMBER);
		assertThat(parameters.getLimit()).isEqualTo(LIMIT);
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getPage()).isEqualTo(PAGE);
		assertThat(parameters.getPostCode()).isEqualTo(POST_CODE);
		assertThat(parameters.getStreet()).isEqualTo(STREET);
		assertThat(parameters.getType()).isEqualTo(TYPE);
	}

	@Test
	void getInstalledBaseDefaultValues() {
		when(serviceMock.getInstalledBase(any())).thenReturn(InstalledBaseResponse.create());

		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/installedbase")
			.queryParams(createParameterMap(null, null, COMPANY, CUSTOMER_NUMBER, TYPE, CARE_OF, STREET, FACILITY_ID, POST_CODE, CITY))
			.build())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InstalledBaseResponse.class)
			.isEqualTo(InstalledBaseResponse.create());

		verify(serviceMock).getInstalledBase(parametersCaptor.capture());
		InstalledBaseParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCareOf()).isEqualTo(CARE_OF);
		assertThat(parameters.getCity()).isEqualTo(CITY);
		assertThat(parameters.getCompany()).isEqualTo(COMPANY);
		assertThat(parameters.getCustomerNumber()).isEqualTo(CUSTOMER_NUMBER);
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_ID);
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
		assertThat(parameters.getPostCode()).isEqualTo(POST_CODE);
		assertThat(parameters.getStreet()).isEqualTo(STREET);
		assertThat(parameters.getType()).isEqualTo(TYPE);
	}

	@Test
	void getInstalledBaseNoParams() {
		when(serviceMock.getInstalledBase(any())).thenReturn(InstalledBaseResponse.create());

		webTestClient.get().uri("/installedbase")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InstalledBaseResponse.class)
			.isEqualTo(InstalledBaseResponse.create());

		verify(serviceMock).getInstalledBase(parametersCaptor.capture());
		InstalledBaseParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getCareOf()).isNull();
		assertThat(parameters.getCity()).isNull();
		assertThat(parameters.getCompany()).isNull();
		assertThat(parameters.getCustomerNumber()).isNull();
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getFacilityId()).isNull();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
		assertThat(parameters.getPostCode()).isNull();
		assertThat(parameters.getStreet()).isNull();
		assertThat(parameters.getType()).isNull();
	}

	private MultiValueMap<String, String> createParameterMap(Integer page, Integer limit, String company, String customerNumber, String type, String careOf, String street, String number, String postCode, String city) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		ofNullable(page).ifPresent(p -> parameters.add("page", p.toString()));
		ofNullable(limit).ifPresent(p -> parameters.add("limit", p.toString()));
		ofNullable(company).ifPresent(p -> parameters.add("company", p));
		ofNullable(customerNumber).ifPresent(p -> parameters.add("customerNumber", p));
		ofNullable(type).ifPresent(p -> parameters.add("type", p));
		ofNullable(careOf).ifPresent(p -> parameters.add("careOf", p));
		ofNullable(street).ifPresent(p -> parameters.add("street", p));
		ofNullable(number).ifPresent(p -> parameters.add("facilityId", p));
		ofNullable(postCode).ifPresent(p -> parameters.add("postCode", p));
		ofNullable(city).ifPresent(p -> parameters.add("city", p));

		return parameters;
	}
}
