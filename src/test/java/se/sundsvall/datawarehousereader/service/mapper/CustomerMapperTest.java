package se.sundsvall.datawarehousereader.service.mapper;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.service.mapper.CustomerMapper.toCustomerEngagements;
import static se.sundsvall.datawarehousereader.service.mapper.CustomerMapper.toPartyType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import generated.se.sundsvall.party.PartyType;
import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagement;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;

class CustomerMapperTest {

	static final int CUSTOMER_ID = 123;
	static final String CUSTOMER_ORG_ID = "customerOrgId";
	static final String CUSTOMER_TYPE = CustomerType.ENTERPRISE.toString();
	static final String ORGANIZATION_ID = "organizationId";
	static final String ORGANIZATION_NAME = "organizationName";

	@Test
	void toCustomersWithNull() {
		assertThat(toCustomerEngagements(null)).isEmpty();
	}

	@Test
	void toCustomersWithEmptyList() {
		assertThat(toCustomerEngagements(Collections.emptyList())).isEmpty();
	}

	@ParameterizedTest
	@MethodSource("toPartyTypeTestArguments")
	void toPartyTypeFromCustomerType(CustomerType customerType, PartyType partyType) {
		assertThat(toPartyType(customerType)).isEqualTo(partyType);
	}

	@Test
	void toCustomers() {
		final var entity = CustomerEntity.create()
			.withCustomerId(CUSTOMER_ID)
			.withCustomerOrgId(CUSTOMER_ORG_ID)
			.withCustomerType(CUSTOMER_TYPE)
			.withOrganizationId(ORGANIZATION_ID)
			.withOrganizationName(ORGANIZATION_NAME);

		final var result = toCustomerEngagements(List.of(entity));

		assertThat(result)
			.hasSize(1)
			.extracting(
				CustomerEngagement::getCustomerNumber,
				CustomerEngagement::getCustomerOrgNumber,
				CustomerEngagement::getCustomerType,
				CustomerEngagement::getOrganizationNumber,
				CustomerEngagement::getOrganizationName,
				CustomerEngagement::getPartyId)
			.containsExactly(tuple(
				valueOf(CUSTOMER_ID),
				CUSTOMER_ORG_ID,
				CustomerType.ENTERPRISE,
				ORGANIZATION_ID,
				ORGANIZATION_NAME,
				null));
	}

	private static Stream<Arguments> toPartyTypeTestArguments() {
		return Stream.of(
			Arguments.of(CustomerType.PRIVATE, PartyType.PRIVATE),
			Arguments.of(CustomerType.ENTERPRISE, PartyType.ENTERPRISE));
	}
}
