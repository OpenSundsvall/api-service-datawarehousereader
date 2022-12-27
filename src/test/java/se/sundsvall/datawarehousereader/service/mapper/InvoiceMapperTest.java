package se.sundsvall.datawarehousereader.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.api.model.invoice.Invoice;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceDetail;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceDetailEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;

class InvoiceMapperTest {

	private static final String ADMINISTRATION = "administration";
	private static final BigDecimal AMOUNT_VAT_EXCLUDED = BigDecimal.valueOf(13.37d);
	private static final BigDecimal AMOUNT_VAT_INCLUDED = BigDecimal.valueOf(13.38d);
	private static final String CARE_OF = "careOf";
	private static final String CITY = "city";
	private static final String CURRENCY = "currency";
	private static final int CUSTOMER_ID = 1337;
	private static final CustomerType CUSTOMER_TYPE = CustomerType.PRIVATE;
	private static final LocalDate DUE_DATE = LocalDate.now().minusDays(30);
	private static final String FACILITY_ID = "facilityId";
	private static final LocalDate INVOICE_DATE = LocalDate.now();
	private static final String INVOICE_DESCRIPTION = "invoiceDescription";
	private static final String INVOICE_NAME = "invoiceName";
	private static final long INVOICE_NUMBER = 4321;
	private static final String INVOICE_STATUS = "invoiceStatus";
	private static final String INVOICE_TYPE = "invoiceType";
	private static final long OCR_NUMBER = 1234;
	private static final String ORGANIZATION_GROUP = "organizationGroup";
	private static final String ORGANIZATION_ID = "organizationId";
	private static final String POSTAL_CODE = "postalCode";
	private static final Boolean REVERSED_VAT = false;
	private static final BigDecimal ROUNDING = BigDecimal.valueOf(13.39d);
	private static final BigDecimal TOTAL_AMOUNT = BigDecimal.valueOf(13.40d);
	private static final BigDecimal VAT = BigDecimal.valueOf(13.41d);
	private static final BigDecimal VAT_ELIGIBLE_AMOUNT = BigDecimal.valueOf(13.42d);
	private static final BigDecimal AMOUNT = BigDecimal.valueOf(13.43d);
	private static final int INVOICE_ID = 7331;
	private static final String PERIOD_FROM = "periodFrom";
	private static final String PERIOD_TO = "periodTo";
	private static final int PRODUCT_CODE = 7371;
	private static final String PRODUCT_NAME = "productName";
	private static final double QUANTITY = 13.44;
	private static final String UNIT = "unit";
	private static final BigDecimal UNIT_PRICE = BigDecimal.valueOf(13.45d);
	private static final double VAT_RATE = 13.46;
	private static final Boolean PDF_AVAILABLE = false;

	private static final String STREET = "street";

	@Test
	void toInvoicesWithNull() {
		assertThat(InvoiceMapper.toInvoices(null)).isEmpty();
	}

	@Test
	void toInvoicesWithEmptyList() {
		assertThat(InvoiceMapper.toInvoices(Collections.emptyList())).isEmpty();
	}

	@Test
	void toInvoices() {
		final var entity = new InvoiceEntity();
		entity.setAdministration(ADMINISTRATION);
		entity.setAmountVatExcluded(AMOUNT_VAT_EXCLUDED);
		entity.setAmountVatIncluded(AMOUNT_VAT_INCLUDED);
		entity.setCareOf(CARE_OF);
		entity.setCity(CITY);
		entity.setCurrency(CURRENCY);
		entity.setCustomerId(CUSTOMER_ID);
		entity.setCustomerType(CUSTOMER_TYPE.getStadsbackenTranslation());
		entity.setDueDate(DUE_DATE);
		entity.setFacilityId(FACILITY_ID);
		entity.setInvoiceDate(INVOICE_DATE);
		entity.setInvoiceDescription(INVOICE_DESCRIPTION);
		entity.setInvoiceName(INVOICE_NAME);
		entity.setInvoiceNumber(INVOICE_NUMBER);
		entity.setInvoiceStatus(INVOICE_STATUS);
		entity.setInvoiceType(INVOICE_TYPE);
		entity.setOcrNumber(OCR_NUMBER);
		entity.setOrganizationGroup(ORGANIZATION_GROUP);
		entity.setOrganizationId(ORGANIZATION_ID);
		entity.setPostCode(POSTAL_CODE);
		entity.setReversedVat(REVERSED_VAT);
		entity.setRounding(ROUNDING);
		entity.setStreet(STREET);
		entity.setTotalAmount(TOTAL_AMOUNT);
		entity.setVat(VAT);
		entity.setVatEligibleAmount(VAT_ELIGIBLE_AMOUNT);
		entity.setPdfAvailable(PDF_AVAILABLE);

		final var result = InvoiceMapper.toInvoices(List.of(entity));

		assertThat(result)
			.hasSize(1)
			.extracting(
				Invoice::getAdministration,
				Invoice::getAmountVatExcluded,
				Invoice::getAmountVatIncluded,
				Invoice::getCareOf,
				Invoice::getCity,
				Invoice::getCurrency,
				Invoice::getCustomerNumber,
				Invoice::getCustomerType,
				Invoice::getDueDate,
				Invoice::getFacilityId,
				Invoice::getInvoiceDate,
				Invoice::getInvoiceDescription,
				Invoice::getInvoiceName,
				Invoice::getInvoiceNumber,
				Invoice::getInvoiceStatus,
				Invoice::getInvoiceType,
				Invoice::getOcrNumber,
				Invoice::getOrganizationNumber,
				Invoice::getOrganizationGroup,
				Invoice::getPostCode,
				Invoice::getReversedVat,
				Invoice::getRounding,
				Invoice::getStreet,
				Invoice::getTotalAmount,
				Invoice::getVat,
				Invoice::getVatEligibleAmount,
				Invoice::getPdfAvailable)
			.containsExactly(tuple(
				ADMINISTRATION,
				AMOUNT_VAT_EXCLUDED,
				AMOUNT_VAT_INCLUDED,
				CARE_OF,
				CITY,
				CURRENCY,
				Integer.toString(CUSTOMER_ID),
				CUSTOMER_TYPE,
				DUE_DATE,
				FACILITY_ID,
				INVOICE_DATE,
				INVOICE_DESCRIPTION,
				INVOICE_NAME,
				INVOICE_NUMBER,
				INVOICE_STATUS,
				INVOICE_TYPE,
				OCR_NUMBER,
				ORGANIZATION_ID,
				ORGANIZATION_GROUP,
				POSTAL_CODE,
				REVERSED_VAT,
				ROUNDING,
				STREET,
				TOTAL_AMOUNT,
				VAT,
				VAT_ELIGIBLE_AMOUNT,
				PDF_AVAILABLE));
	}

	@Test
	void toDetailsWithNull() {
		assertThat(InvoiceMapper.toDetails(null)).isEmpty();
	}

	@Test
	void toDetailsWithEmptyList() {
		assertThat(InvoiceMapper.toDetails(Collections.emptyList())).isEmpty();
	}

	@Test
	void toDetails() {
		final var entity = new InvoiceDetailEntity();
		entity.setAmount(AMOUNT);
		entity.setAmountVatExcluded(AMOUNT_VAT_EXCLUDED);
		entity.setDescription(INVOICE_DESCRIPTION);
		entity.setInvoiceId(INVOICE_ID);
		entity.setInvoiceNumber(INVOICE_NUMBER);
		entity.setOrganizationId(ORGANIZATION_ID);
		entity.setPeriodFrom(PERIOD_FROM);
		entity.setPeriodTo(PERIOD_TO);
		entity.setProductCode(PRODUCT_CODE);
		entity.setProductName(PRODUCT_NAME);
		entity.setQuantity(QUANTITY);
		entity.setUnit(UNIT);
		entity.setUnitPrice(UNIT_PRICE);
		entity.setVat(VAT);
		entity.setVatRate(VAT_RATE);

		final var result = InvoiceMapper.toDetails(List.of(entity));

		assertThat(result)
			.hasSize(1)
			.extracting(
				InvoiceDetail::getAmount,
				InvoiceDetail::getAmountVatExcluded,
				InvoiceDetail::getDescription,
				InvoiceDetail::getInvoiceNumber,
				InvoiceDetail::getOrganizationNumber,
				InvoiceDetail::getPeriodFrom,
				InvoiceDetail::getPeriodTo,
				InvoiceDetail::getProductCode,
				InvoiceDetail::getProductName,
				InvoiceDetail::getQuantity,
				InvoiceDetail::getUnit,
				InvoiceDetail::getUnitPrice,
				InvoiceDetail::getVat,
				InvoiceDetail::getVatRate)
			.containsExactly(tuple(
				AMOUNT,
				AMOUNT_VAT_EXCLUDED,
				INVOICE_DESCRIPTION,
				INVOICE_NUMBER,
				ORGANIZATION_ID,
				PERIOD_FROM,
				PERIOD_TO,
				PRODUCT_CODE,
				PRODUCT_NAME,
				QUANTITY,
				UNIT,
				UNIT_PRICE,
				VAT,
				VAT_RATE));
	}
}
