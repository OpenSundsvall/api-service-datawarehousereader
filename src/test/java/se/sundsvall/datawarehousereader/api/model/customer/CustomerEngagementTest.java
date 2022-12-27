package se.sundsvall.datawarehousereader.api.model.customer;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.CustomerType;

class CustomerEngagementTest {

	@Test
	void testBean() {
		assertThat(CustomerEngagement.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var customerNumber = "101";
		final var partyId = "partyId";
		final var customerType = CustomerType.PRIVATE;
		final var organizationNumber = "organizationNumber";
		final var organizationName = "organizationName";

		final var customer = CustomerEngagement.create()
			.withCustomerNumber(customerNumber)
			.withPartyId(partyId)
			.withCustomerType(customerType)
			.withOrganizationNumber(organizationNumber)
			.withOrganizationName(organizationName);

		assertThat(customer.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(customer.getPartyId()).isEqualTo(partyId);
		assertThat(customer.getCustomerType()).isEqualTo(customerType);
		assertThat(customer.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(customer.getOrganizationName()).isEqualTo(organizationName);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerEngagement.create()).hasAllNullFieldsOrProperties();
		assertThat(new CustomerEngagement()).hasAllNullFieldsOrProperties();
	}
}
