package se.sundsvall.datawarehousereader.integration.stadsbacken.specification;

import static java.util.Objects.nonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;

public interface AgreementSpecification {

	static Specification<AgreementEntity> withAgreementId(Integer agreementId) {
		return buildEqualFilter("agreementId", agreementId);
	}

	static Specification<AgreementEntity> withBillingId(Integer billingId) {
		return buildEqualFilter("billingId", billingId);
	}

	static Specification<AgreementEntity> withCustomerOrgId(String customerOrgId) {
		return buildEqualFilter("customerOrgId", customerOrgId);
	}

	static Specification<AgreementEntity> withCustomerId(Integer customerId) {
		return buildEqualFilter("customerId", customerId);
	}

	static Specification<AgreementEntity> withFacilityId(String facilityId) {
		return buildEqualFilter("facilityId", facilityId);
	}

	static Specification<AgreementEntity> withCategories(List<Category> categories) {
		return (invoiceEntity, cq, cb) -> isEmpty(categories) ? cb.and() : addToInClause(cb.in(invoiceEntity.get("category")), categories);
	}

	static Specification<AgreementEntity> withDescription(String description) {
		return buildEqualFilter("description", description);
	}

	static Specification<AgreementEntity> withMainAgreement(String mainAgreement) {
		return buildEqualFilter("mainAgreement", mainAgreement);
	}

	static Specification<AgreementEntity> withBinding(String binding) {
		return buildEqualFilter("binding", binding);
	}

	static Specification<AgreementEntity> withBindingRule(String bindingRule) {
		return buildEqualFilter("bindingRule", bindingRule);
	}

	static Specification<AgreementEntity> withFromDate(LocalDate date) {
		return (agreementEntity, cq, cb) -> nonNull(date) ? cb.between(agreementEntity.get("fromDate"), date.atStartOfDay(), date.atTime(LocalTime.MAX)) : cb.and();
	}

	static Specification<AgreementEntity> withToDate(LocalDate date) {
		return (agreementEntity, cq, cb) -> nonNull(date) ? cb.between(agreementEntity.get("toDate"), date.atStartOfDay(), date.atTime(LocalTime.MAX)) : cb.and();
	}

	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * 
	 * @param attribute name that will be used in filter
	 * @param value     value (or null) to compare against
	 * @return Specification<AgreementEntity> matching sent in comparison
	 */
	private static Specification<AgreementEntity> buildEqualFilter(String attribute, Object value) {
		return (agreementEntity, cq, cb) -> nonNull(value) ? cb.equal(agreementEntity.get(attribute), value) : cb.and();
	}

	private static In<String> addToInClause(In<String> clause, List<Category> values) {
		values.stream()
			.map(Category::toStadsbackenValue)
			.forEach(clause::value);

		return clause;
	}
}
