package se.sundsvall.datawarehousereader.apptest.measurment;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_HEATING;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

/**
 * Read district-heating-measurements tests
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@WireMockAppTestSuite(files = "classpath:/GetMeasurementsDistrictHeating/", classes = Application.class)
class GetMeasurementsDistrictHeatingIT extends AbstractAppTest {
	
	private static final String PATH = "/measurements/%s/%s";
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getDistrictHeatingMonth() {
		setupCall()
				.withServicePath(format(PATH, DISTRICT_HEATING, MONTH) +
						"/?page=1" +
						"&limit=100" +
						"&partyId=B1EDEA3C-1083-4E1A-81FB-7D95E505E102" +
						"&facilityId=735999109113202014" +
						"&fromDateTime=2018-01-01T14:39:22.817Z" +
						"&toDateTime=2018-12-31T14:39:22.817Z")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getDistrictHeatingWithCustomPageAndLimit() {
		setupCall()
				.withServicePath(format(PATH, DISTRICT_HEATING, MONTH) +
						"/?page=2" +
						"&limit=10" +
						"&partyId=B1EDEA3C-1083-4E1A-81FB-7D95E505E102" +
						"&facilityId=735999109113202014" +
						"&fromDateTime=2018-01-01T14:39:22.817Z" +
						"&toDateTime=2018-12-31T14:39:22.817Z")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

}
