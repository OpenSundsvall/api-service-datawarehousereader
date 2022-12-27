package se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.jupiter.api.Test;

class CustomerEntityTest {

	@Test
	void testBean() {
		assertThat(CustomerEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var customerId = 123;
		final var customerOrgId = "customerOrgId";
		final var customerType = "customerType";
		final var organizationId = "organizationId";
		final var organizationName = "organizationName";

		final var entity = CustomerEntity.create()
			.withCustomerId(customerId)
			.withCustomerOrgId(customerOrgId)
			.withCustomerType(customerType)
			.withOrganizationId(organizationId)
			.withOrganizationName(organizationName);

		assertThat(entity.getCustomerId()).isEqualTo(customerId);
		assertThat(entity.getCustomerOrgId()).isEqualTo(customerOrgId);
		assertThat(entity.getCustomerType()).isEqualTo(customerType);
		assertThat(entity.getOrganizationId()).isEqualTo(organizationId);
		assertThat(entity.getOrganizationName()).isEqualTo(organizationName);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerEntity.create()).hasAllNullFieldsOrProperties();
		assertThat(new CustomerEntity()).hasAllNullFieldsOrProperties();
	}
}
