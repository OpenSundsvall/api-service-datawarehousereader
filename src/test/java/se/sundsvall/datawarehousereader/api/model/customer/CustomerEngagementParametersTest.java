package se.sundsvall.datawarehousereader.api.model.customer;

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
import org.springframework.data.domain.Sort;

class CustomerEngagementParametersTest {

	@Test
	void testBean() {
		assertThat(CustomerEngagementParameters.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var customerNumber = "customerNumber";
		final var partyId = List.of("partyId");
		final var organizationName = "organizationName";
		final var organizationNumber = "organizationNumber";

		final var customerParameters = CustomerEngagementParameters.create()
			.withCustomerNumber(customerNumber)
			.withOrganizationName(organizationName)
			.withOrganizationNumber(organizationNumber)
			.withPartyId(partyId);

		assertThat(customerParameters.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(customerParameters.getOrganizationName()).isEqualTo(organizationName);
		assertThat(customerParameters.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(customerParameters.getPartyId()).isEqualTo(partyId);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerEngagementParameters.create())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortBy", "sortDirection")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100)
			.hasFieldOrPropertyWithValue("sortDirection", Sort.DEFAULT_DIRECTION);

		assertThat(new CustomerEngagementParameters())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortBy", "sortDirection")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100)
			.hasFieldOrPropertyWithValue("sortDirection", Sort.DEFAULT_DIRECTION);
	}
}
