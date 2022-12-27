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

import se.sundsvall.datawarehousereader.api.model.MetaData;

class CustomerEngagementResponseTest {

	@Test
	void testBean() {
		assertThat(CustomerEngagementResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var metaData = MetaData.create();
		final var customer = CustomerEngagement.create();

		final var response = CustomerEngagementResponse.create()
			.withMetaData(metaData)
			.withCustomerEngagements(List.of(customer));

		assertThat(response.getMetaData()).isEqualTo(metaData);
		assertThat(response.getCustomerEngagements()).hasSize(1).contains(customer);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerEngagementResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new CustomerEngagementResponse()).hasAllNullFieldsOrProperties();
	}
}
