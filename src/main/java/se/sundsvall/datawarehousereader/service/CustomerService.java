package se.sundsvall.datawarehousereader.service;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.util.StringUtils.hasText;
import static se.sundsvall.datawarehousereader.service.mapper.CustomerMapper.toCustomerEngagements;
import static se.sundsvall.datawarehousereader.service.mapper.CustomerMapper.toPartyType;
import static se.sundsvall.datawarehousereader.service.util.ServiceUtil.removeHyphen;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.datawarehousereader.api.model.MetaData;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagement;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagementParameters;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagementResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.CustomerRepository;
import se.sundsvall.datawarehousereader.service.logic.PartyProvider;

@Service
public class CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private PartyProvider partyProvider;

	public CustomerEngagementResponse getCustomerEngagements(CustomerEngagementParameters parameters) {
		final var matches = repository.findAllByParameters(parameters, getCustomerOrgIdList(parameters.getPartyId()), of(parameters.getPage() - 1, parameters.getLimit(), parameters.sort()));

		LOGGER.debug("Database query results: {} with content: {}", matches, matches.getContent());

		// If page larger than last page is requested, a empty list is returned otherwise the current page
		List<CustomerEngagement> customerEngagements = matches.getTotalPages() < parameters.getPage() ? emptyList() : switchToPartyId(toCustomerEngagements(matches.getContent()));

		return CustomerEngagementResponse.create()
			.withMetaData(MetaData.create()
				.withPage(parameters.getPage())
				.withSortBy(parameters.getSortBy())
				.withSortDirection(parameters.getSortDirection())
				.withTotalPages(matches.getTotalPages())
				.withTotalRecords(matches.getTotalElements())
				.withCount(customerEngagements.size())
				.withLimit(parameters.getLimit()))
			.withCustomerEngagements(customerEngagements);
	}

	private List<String> getCustomerOrgIdList(List<String> partyIds) {
		return ofNullable(partyIds).orElse(emptyList()).stream()
			.map(partyProvider::translateToLegalId)
			.toList();
	}

	private List<CustomerEngagement> switchToPartyId(List<CustomerEngagement> customerEngagements) {
		customerEngagements.stream()
			.forEach(engagement -> engagement
				.withPartyId(fetchPartyId(engagement))
				.withCustomerOrgNumber(null)); // Needs to be reset to not expose person/organization number in response

		return customerEngagements;
	}

	private String fetchPartyId(CustomerEngagement engagement) {
		if (!hasText(engagement.getCustomerOrgNumber())) {
			LOGGER.info("CustomerEngagement did not contain a 'customerOrgNumber'. Skipping call to Party-service. {}", engagement);
			return null;
		}
		return partyProvider.translateToPartyId(toPartyType(engagement.getCustomerType()), removeHyphen(engagement.getCustomerOrgNumber()));
	}
}
