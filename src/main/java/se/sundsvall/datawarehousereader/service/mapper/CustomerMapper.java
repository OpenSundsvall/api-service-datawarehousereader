package se.sundsvall.datawarehousereader.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static se.sundsvall.datawarehousereader.Constants.UNKNOWN_CUSTOMER_TYPE;
import static se.sundsvall.datawarehousereader.api.model.CustomerType.fromValue;

import java.util.List;

import generated.se.sundsvall.party.PartyType;
import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagement;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

public class CustomerMapper {

	private CustomerMapper() {}

	public static List<CustomerEngagement> toCustomerEngagements(List<CustomerEntity> entities) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.map(CustomerMapper::toCustomerEngagement)
			.toList();
	}

	public static PartyType toPartyType(CustomerType customerType) {
		return switch (customerType) {
			case PRIVATE -> PartyType.PRIVATE;
			case ENTERPRISE -> PartyType.ENTERPRISE;
		};
	}

	private static CustomerEngagement toCustomerEngagement(CustomerEntity entity) {
		return CustomerEngagement.create()
			.withCustomerNumber(ServiceUtil.toString(entity.getCustomerId()))
			.withCustomerOrgNumber(entity.getCustomerOrgId())
			.withCustomerType(fromValue(entity.getCustomerType(), INTERNAL_SERVER_ERROR, UNKNOWN_CUSTOMER_TYPE))
			.withOrganizationNumber(entity.getOrganizationId())
			.withOrganizationName(entity.getOrganizationName());
	}
}
