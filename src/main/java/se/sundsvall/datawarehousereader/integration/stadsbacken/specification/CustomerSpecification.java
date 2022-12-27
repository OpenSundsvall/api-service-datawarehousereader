package se.sundsvall.datawarehousereader.integration.stadsbacken.specification;

import static java.util.Objects.nonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;

public interface CustomerSpecification {

	static Specification<CustomerEntity> withCustomerOrgIds(List<String> customerOrgIds) {
		return (customerEntity, cq, cb) -> isEmpty(customerOrgIds) ? cb.and() : addToInClause(cb.in(customerEntity.get("customerOrgId")), customerOrgIds);
	}

	static Specification<CustomerEntity> withOrganizationId(String organizationId) {
		return buildEqualFilter("organizationId", organizationId);
	}

	static Specification<CustomerEntity> withOrganizationName(String organizationName) {
		return buildEqualFilter("organizationName", organizationName);
	}

	static Specification<CustomerEntity> withCustomerId(Integer customerId) {
		return buildEqualFilter("customerId", customerId);
	}

	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * 
	 * @param attribute name that will be used in filter
	 * @param value     value (or null) to compare against
	 * @return Specification<CustomerEntity> matching sent in comparison
	 */
	private static Specification<CustomerEntity> buildEqualFilter(String attribute, Object value) {
		return (customerEntity, cq, cb) -> nonNull(value) ? cb.equal(customerEntity.get(attribute), value) : cb.and();
	}

	private static In<String> addToInClause(In<String> clause, List<String> values) {
		values.stream()
			.forEach(clause::value);

		return clause;
	}
}
