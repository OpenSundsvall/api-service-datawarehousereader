package se.sundsvall.datawarehousereader.api.model.invoice;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class InvoiceDetailTest {

	@Test
	void testBean() {
		assertThat(InvoiceDetail.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var amount = valueOf(101);
		final var amountVatExcluded = valueOf(202);
		final var description = "description";
		final var invoiceNumber = 1337L;
		final var periodFrom = "periodFrom";
		final var periodTo = "periodTo";
		final var productCode = 4321;
		final var productName = "productName";
		final var quantity = 666D;
		final var unit = "unit";
		final var unitPrice = valueOf(303);
		final var vat = valueOf(404);
		final var vatRate = 777D;
		final var organizationNumber = "organizationNumber";

		final var detail = InvoiceDetail.create()
			.withAmount(amount)
			.withAmountVatExcluded(amountVatExcluded)
			.withDescription(description)
			.withInvoiceNumber(invoiceNumber)
			.withPeriodFrom(periodFrom)
			.withPeriodTo(periodTo)
			.withProductCode(productCode)
			.withProductName(productName)
			.withQuantity(quantity)
			.withUnit(unit)
			.withUnitPrice(unitPrice)
			.withVat(vat)
			.withVatRate(vatRate)
			.withOrganizationNumber(organizationNumber);

		assertThat(detail.getAmount()).isEqualTo(amount);
		assertThat(detail.getAmountVatExcluded()).isEqualTo(amountVatExcluded);
		assertThat(detail.getDescription()).isEqualTo(description);
		assertThat(detail.getInvoiceNumber()).isEqualTo(invoiceNumber);
		assertThat(detail.getPeriodFrom()).isEqualTo(periodFrom);
		assertThat(detail.getPeriodTo()).isEqualTo(periodTo);
		assertThat(detail.getProductCode()).isEqualTo(productCode);
		assertThat(detail.getProductName()).isEqualTo(productName);
		assertThat(detail.getQuantity()).isEqualTo(quantity);
		assertThat(detail.getUnit()).isEqualTo(unit);
		assertThat(detail.getUnitPrice()).isEqualTo(unitPrice);
		assertThat(detail.getVat()).isEqualTo(vat);
		assertThat(detail.getOrganizationNumber()).isEqualTo(organizationNumber);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoiceDetail.create()).hasAllNullFieldsOrProperties();
		assertThat(new InvoiceDetail()).hasAllNullFieldsOrProperties();
	}
}
