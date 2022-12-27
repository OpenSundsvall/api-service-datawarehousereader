package se.sundsvall.datawarehousereader.apptest.customer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

/**
 * Get customer engagements tests
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@WireMockAppTestSuite(files = "classpath:/GetCustomerEngagements/", classes = Application.class)
class GetCustomerEngagementsIT extends AbstractAppTest {

	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getByCustomerNumber() {
		setupCall()
			.withServicePath("/customer/engagements?customerNumber=600606")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getByOrganizationNumber() {
		setupCall()
			.withServicePath("/customer/engagements?organizationNumber=5564786647")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getByOrganizationName() {
		setupCall()
			.withServicePath("/customer/engagements?organizationName=Sundsvall Energi AB")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test04_getPrivateCustomerByPartyId() {
		setupCall()
			.withServicePath("/customer/engagements?partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test05_getByAllParametersWithMatch() {
		setupCall()
			.withServicePath("/customer/engagements"
				+ "?partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1"
				+ "&organizationName=Sundsvall Energi AB"
				+ "&organizationNumber=5564786647&"
				+ "&customerNumber=691071")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test06_getByAllParametersWithNoMatch() {
		setupCall()
			.withServicePath("/customer/engagements"
				+ "?partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1"
				+ "&organizationName=Elbolaget AB"
				+ "&organizationNumber=5564786647&"
				+ "&customerNumber=691071")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test07_getEnterpriseCustomerByPartyId() {
		setupCall()
			.withServicePath("/customer/engagements?partyId=70933c1c-2094-44e6-9abe-57b8a3c0ecd9")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test08_getByMultiplePartyIds() {
		setupCall()
			.withServicePath("/customer/engagements?partyId=70933c1c-2094-44e6-9abe-57b8a3c0ecd9&partyId=318b7ec8-aab8-41e2-82f5-796bdd5cebf2&partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
