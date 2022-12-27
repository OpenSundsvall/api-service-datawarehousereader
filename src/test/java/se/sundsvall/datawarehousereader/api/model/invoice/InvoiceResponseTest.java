package se.sundsvall.datawarehousereader.api.model.invoice;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.MetaData;

class InvoiceResponseTest {

	@Test
	void testBean() {
		assertThat(InvoiceResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var metaData = MetaData.create();
		final var invoice = Invoice.create();

		final var response = InvoiceResponse.create()
			.withMetaData(metaData)
			.withInvoices(List.of(invoice));

		assertThat(response.getMetaData()).isEqualTo(metaData);
		assertThat(response.getInvoices()).hasSize(1).contains(invoice);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoiceResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new InvoiceResponse()).hasAllNullFieldsOrProperties();
	}
}
