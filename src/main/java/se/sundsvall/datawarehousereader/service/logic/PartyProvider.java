package se.sundsvall.datawarehousereader.service.logic;

import static generated.se.sundsvall.party.PartyType.ENTERPRISE;
import static generated.se.sundsvall.party.PartyType.PRIVATE;
import static java.lang.String.format;
import static org.zalando.problem.Problem.valueOf;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import generated.se.sundsvall.party.PartyType;
import se.sundsvall.datawarehousereader.integration.party.PartyClient;

@Component
public class PartyProvider {

	@Autowired
	private PartyClient partyClient;

	public String translateToLegalId(String partyId) {
		return partyClient.getLegalId(PRIVATE, partyId)
			.or(() -> partyClient.getLegalId(ENTERPRISE, partyId))
			.orElseThrow(() -> valueOf(NOT_FOUND, format("PartyId '%s' could not be found as a private customer or an enterprise customer", partyId)));
	}
	
	public String translateToPartyId(PartyType partyType, String legalId) {
		return partyClient.getPartyId(partyType, legalId)
			.orElseThrow(() -> valueOf(INTERNAL_SERVER_ERROR, "Could not determine partyId for customer connected to returned data"));
	}
}
