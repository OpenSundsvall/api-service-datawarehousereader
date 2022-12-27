package se.sundsvall.datawarehousereader.apptest.invoices;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

/**
 * Read invoices tests
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@WireMockAppTestSuite(files = "classpath:/GetInvoices/", classes = Application.class)
class GetInvoicesIT extends AbstractAppTest {

	private static final String PATH = "/invoices";
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getWithNoParams() {
		setupCall()
			.withServicePath(PATH)
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getWithCustomPageAndLimit() {
		setupCall()
			.withServicePath(PATH.concat("?page=18&limit=10"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getByCustomerNumber() {
		setupCall()
			.withServicePath(PATH.concat("?customerNumber=600606"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test04_getByCustomerType() {
		setupCall()
			.withServicePath(PATH.concat("?customerType=Private"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test05_getByFacilityId() {
		setupCall()
			.withServicePath(PATH.concat("?facilityId=735999109122811214"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test06_getByInvoiceNumber() {
		setupCall()
			.withServicePath(PATH.concat("?invoiceNumber=137968392"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test07_getByInvoiceDateBetween() {
		setupCall()
			.withServicePath(PATH.concat("?invoiceDateFrom=2019-10-11&invoiceDateTo=2019-10-20"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test08_getByInvoiceName() {
		setupCall()
			.withServicePath(PATH.concat("?invoiceName=766763197.pdf"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test09_getByInvoiceType() {
		setupCall()
			.withServicePath(PATH.concat("?invoiceType=Kreditfaktura"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test10_getByInvoiceStatus() {
		setupCall()
			.withServicePath(PATH.concat("?invoiceStatus=Krediterad"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test11_getByOcrNumber() {
		setupCall()
			.withServicePath(PATH.concat("?ocrNumber=139346696"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test12_getByDueDateBetween() {
		setupCall()
			.withServicePath(PATH.concat("?dueDateFrom=2019-10-20&dueDateTo=2019-10-31"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test13_getByAdministration() {
		setupCall()
			.withServicePath(PATH.concat("?administration=Sundsvall Energi AB"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test14_getByCombinationWithMatches() {
		setupCall()
			.withServicePath(PATH.concat(
				"?customerId=10335" +
					"&customerType=Enterprise" +
					"&facilityIds=735999109224602000" +
					"&invoiceNumber=139346993" +
					"&invoiceDateFrom=2019-10-10" +
					"&invoiceName=139346993.pdf" +
					"&invoiceType=Faktura" +
					"&invoiceStatus=Skickad" +
					"&ocrNumber=139346993" +
					"&dueDateTo=2019-11-11" +
					"&organizationGroup=stadsbacken" +
					"&administration=Sundsvall Elnät"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test15_getByCombinationWithoutMatches() {
		setupCall()
			.withServicePath(PATH.concat(
				"?customerId=10335" +
					"&customerType=Enterprise" +
					"&facilityIds=735999109224602000" +
					"&invoiceNumber=139346993" +
					"&invoiceDateFrom=2019-10-10" +
					"&invoiceName=139346993.pdf" +
					"&invoiceType=Faktura" +
					"&invoiceStatus=Skickad" +
					"&ocrNumber=139346993" +
					"&dueDateTo=2019-11-10" +
					"&organizationGroup=stadsbacken" +
					"&administration=Sundsvall Elnät"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test16_getWithPageLargerThanResult() {
		setupCall()
			.withServicePath(PATH.concat("?page=3"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test17_getByOrganizationNumber() {
		setupCall()
			.withServicePath(PATH.concat("?organizationNumber=5564786647"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test18_getByCustomerNumbers() {
		setupCall()
			.withServicePath(PATH.concat("?customerNumber=600606&customerNumber=600675"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test19_getByFacilityIds() {
		setupCall()
			.withServicePath(PATH.concat("?facilityId=735999109122811214&facilityId=735999109144511017"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test20_getAllSortByMultipleProperties() {
		setupCall()
			.withServicePath(PATH.concat("?sortBy=invoiceDescription&sortBy=invoiceDate&sortDirection=ASC&limit=200"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
