package se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.jupiter.api.Test;

class InvoiceDetailEntityTest {

	@Test
	void testBean() {
		assertThat(InvoiceDetailEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(new InvoiceDetailEntity())
			.hasAllNullFieldsOrPropertiesExcept("invoiceProductSeq", "invoiceId", "productCode")
			.hasFieldOrPropertyWithValue("invoiceProductSeq", 0)
			.hasFieldOrPropertyWithValue("invoiceId", 0)
			.hasFieldOrPropertyWithValue("productCode", 0);
	}
}
