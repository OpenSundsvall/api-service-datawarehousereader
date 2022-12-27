package se.sundsvall.datawarehousereader.service.mapper;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.agreement.Agreement;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.service.mapper.AgreementMapper.toAgreements;

class AgreementMapperTest {

	static final int CUSTOMER_ID = 123;
	static final String CUSTOMER_ORG_ID = "customerOrgId";
	private static final Integer AGREEMENT_ID = 1112222;
	private static final Integer BILLING_ID = 222333;
	private static final String PARTY_ID = UUID.randomUUID().toString();
	private static final Category CATEGORY = ELECTRICITY;
	private static final String FACILITY_ID = "facilityId";
	private static final String DESCRIPTION = "description";
	private static final Boolean MAIN_AGREEMENT = true;
	private static final Boolean BINDING = false;
	private static final String BINDING_RULE = "bindingRule";
	private static final LocalDate FROM_DATE = LocalDate.now().minusMonths(10L);
	private static final LocalDate TO_DATE = LocalDate.now();

	@Test
	void toAgreementsWithNull() {
		assertThat( toAgreements(null)).isEmpty();
	}

	@Test
	void toAgreementsWithEmptyList() {
		assertThat(toAgreements(Collections.emptyList())).isEmpty();
	}

	@Test
	void toAgreementsWithAllParameters() {
		final var entity = AgreementEntity.create()
			.withCustomerId(CUSTOMER_ID)
			.withCustomerOrgId(CUSTOMER_ORG_ID)
			.withAgreementId(AGREEMENT_ID)
			.withBillingId(BILLING_ID)
			.withCategory(CATEGORY.toStadsbackenValue())
			.withMainAgreement(MAIN_AGREEMENT.toString())
			.withBinding(BINDING.toString())
			.withBindingRule(BINDING_RULE)
			.withUuid(PARTY_ID)
			.withFacilityId(FACILITY_ID)
			.withDescription(DESCRIPTION)
			.withFromDate(FROM_DATE.atStartOfDay())
			.withToDate(TO_DATE.atStartOfDay());

		final var result = toAgreements(List.of(entity));

		assertThat(result)
			.hasSize(1)
			.extracting(
				Agreement::getCustomerNumber,
				Agreement::getPartyId,
				Agreement::getAgreementId,
				Agreement::getBillingId,
				Agreement::getMainAgreement,
				Agreement::getBinding,
				Agreement::getBindingRule,
				Agreement::getCategory,
				Agreement::getFromDate,
				Agreement::getToDate,
				Agreement::getDescription,
				Agreement::getFacilityId)
			.containsExactly(tuple(
				valueOf(CUSTOMER_ID),
				PARTY_ID,
				AGREEMENT_ID.toString(),
				BILLING_ID.toString(),
				MAIN_AGREEMENT,
				BINDING,
				BINDING_RULE,
				CATEGORY,
				FROM_DATE,
				TO_DATE,
				DESCRIPTION,
				FACILITY_ID));
	}
}
