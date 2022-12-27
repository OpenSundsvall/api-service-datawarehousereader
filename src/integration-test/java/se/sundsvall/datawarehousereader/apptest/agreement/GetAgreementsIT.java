package se.sundsvall.datawarehousereader.apptest.agreement;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

/**
 * Get agreements tests
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@WireMockAppTestSuite(files = "classpath:/GetAgreements/", classes = Application.class)
class GetAgreementsIT extends AbstractAppTest {

	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getAgreementsByCustomerNumber() {
		setupCall()
			.withServicePath("/agreements?customerNumber=634212")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getAgreementsByPartyId() {
		setupCall()
			.withServicePath("/agreements?partyId=336EC35A-3335-4FA3-B792-60061222B0E9")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getAgreementsByAllParametersWithMatch() {
		setupCall()
				.withServicePath("/agreements"
						+ "?partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1"
						+ "&agreementId=43985"
						+ "&billingId=2049476"
						+ "&customerNumber=634635"
						+ "&category=ELECTRICITY"
						+ "&facilityId=735999109452782017"
						+ "&mainAgreement=true"
						+ "&binding=false"
						+ "&bindingRule=Fastpris 2 år"
						+ "&description=Fastpris 2 år"
						+ "&fromDate=2022-01-01"
						+ "&toDate=2022-07-01")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

	@Test
	void test04_getAgreementsByAllParametersWithNoMatch() {
		setupCall()
				.withServicePath("/agreements"
						+ "?partyId=10e69eb7-3cb4-442f-8cdb-2e998080dbb1"
						+ "&agreementId=43985"
						+ "&billingId=2049476"
						+ "&customerNumber=634635"
						+ "&category=ELECTRICITY"
						+ "&facilityId=no-match"
						+ "&mainAgreement=true"
						+ "&binding=false"
						+ "&bindingRule=Fastpris 2 år"
						+ "&description=Fastpris 2 år"
						+ "&fromDate=2022-01-01"
						+ "&toDate=2022-07-01")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

	@Test
	void test05_getWithPageLargerThanResult() {
		setupCall()
				.withServicePath("/agreements?partyId=8B718423-8840-4CE5-AB95-642DD876C887&page=2")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

	@Test
	void test06_getPageWithCustomValues() {
		setupCall()
				.withServicePath("/agreements?category=DISTRICT_HEATING&limit=1&page=5")
				.withHttpMethod(GET)
				.withExpectedResponseStatus(OK)
				.withExpectedResponse(RESPONSE_FILE)
				.sendRequestAndVerifyResponse();
	}

	@Test
	void test07_getAgreementsByPartyIdAndMultipleCategories() {
		setupCall()
			.withServicePath("/agreements?partyId=65E78C52-B2E1-42DD-8D42-828A94E5BAFF&category=ELECTRICITY&category=DISTRICT_HEATING")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
