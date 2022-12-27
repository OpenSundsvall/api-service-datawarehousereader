package se.sundsvall.datawarehousereader.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.agreement.Agreement;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

public class AgreementMapper {

	private AgreementMapper() {}

	public static List<Agreement> toAgreements(List<AgreementEntity> entities) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.map(AgreementMapper::toAgreement)
			.toList();
	}

	private static Agreement toAgreement(AgreementEntity entity) {
		return Agreement.create()
			.withCustomerNumber(ServiceUtil.toString(entity.getCustomerId()))
			.withBillingId(ServiceUtil.toString(entity.getBillingId()))
			.withAgreementId(ServiceUtil.toString(entity.getAgreementId()))
			.withPartyId(entity.getUuid())
			.withDescription(entity.getDescription())
			.withCategory(getCategory(entity.getCategory()))
			.withBinding(Boolean.valueOf(entity.getBinding()))
			.withBindingRule(entity.getBindingRule())
			.withMainAgreement(Boolean.valueOf(entity.getMainAgreement()))
			.withFacilityId(entity.getFacilityId())
			.withFromDate(toLocalDate(entity.getFromDate()))
			.withToDate(toLocalDate(entity.getToDate()));
	}

	private static LocalDate toLocalDate(LocalDateTime localDateTime) {
		return ofNullable(localDateTime)
			.map(LocalDateTime::toLocalDate)
			.orElse(null);
	}

	private static Category getCategory(String category) {
		return ofNullable(category)
			.map(Category::forStadsbackenValue)
			.orElse(null);
	}
}
