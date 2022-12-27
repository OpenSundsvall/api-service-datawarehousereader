package se.sundsvall.datawarehousereader.service;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;
import static se.sundsvall.datawarehousereader.service.mapper.AgreementMapper.toAgreements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.datawarehousereader.api.model.MetaData;
import se.sundsvall.datawarehousereader.api.model.agreement.Agreement;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.AgreementRepository;
import se.sundsvall.datawarehousereader.service.logic.PartyProvider;

@Service
public class AgreementService {

	@Autowired
	private AgreementRepository repository;

	@Autowired
	private PartyProvider partyProvider;

	public AgreementResponse getAgreements(AgreementParameters parameters) {
		final var matches = repository.findAllByParameters(parameters, getCustomerOrgId(parameters.getPartyId()), of(parameters.getPage() - 1, parameters.getLimit(), parameters.sort()));

		// If page larger than last page is requested, a empty list is returned otherwise the current page
		List<Agreement> agreements = matches.getTotalPages() < parameters.getPage() ? emptyList() : toAgreements(matches.getContent());

		return AgreementResponse.create()
			.withMetaData(MetaData.create()
				.withPage(parameters.getPage())
				.withSortBy(parameters.getSortBy())
				.withSortDirection(parameters.getSortDirection())
				.withTotalPages(matches.getTotalPages())
				.withTotalRecords(matches.getTotalElements())
				.withCount(agreements.size())
				.withLimit(parameters.getLimit()))
			.withAgreements(agreements);
	}

	private String getCustomerOrgId(String partyId) {
		return ofNullable(partyId)
			.map(partyProvider::translateToLegalId)
			.orElse(null);
	}
}
