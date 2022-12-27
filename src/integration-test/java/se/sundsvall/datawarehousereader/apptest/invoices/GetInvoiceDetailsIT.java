package se.sundsvall.datawarehousereader.apptest.invoices;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
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
@WireMockAppTestSuite(files = "classpath:/GetInvoiceDetails/", classes = Application.class)
class GetInvoiceDetailsIT extends AbstractAppTest {
	
	private static final String PATH = "/invoices/%s/%s/details";
	private static final String PATH_DEPRECATED = "/invoices/%s/details";
	private static final String RESPONSE_FILE = "response.json";
	
	@Test
	void test01_getDetailsForExistingInvoice() {
		setupCall()
			.withServicePath(String.format(PATH, "5565027223", "139342893"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getDetailsForNonExistingInvoice() {
		setupCall()
			.withServicePath(String.format(PATH, "5565027223", "999999999"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getDeprecatedDetailsForExistingInvoice() {
		setupCall()
			.withServicePath(String.format(PATH_DEPRECATED, "139342893"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test04_getDeprecatedDetailsForNonExistingInvoice() {
		setupCall()
			.withServicePath(String.format(PATH_DEPRECATED, "999999999"))
			.withHttpMethod(GET)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
