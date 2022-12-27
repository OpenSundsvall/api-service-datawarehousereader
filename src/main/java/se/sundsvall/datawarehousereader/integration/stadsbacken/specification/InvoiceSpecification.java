package se.sundsvall.datawarehousereader.integration.stadsbacken.specification;

import static java.util.Objects.nonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.datawarehousereader.api.model.CustomerType;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;

public interface InvoiceSpecification {

	static Specification<InvoiceEntity> withCustomerIds(List<Integer> customerIds) {
		return (invoiceEntity, cq, cb) -> isEmpty(customerIds) ? cb.and() : addToInIntegerClause(cb.in(invoiceEntity.get("customerId")), customerIds);
	}

	static Specification<InvoiceEntity> withCustomerType(CustomerType customerType) {
		return buildEqualFilter("customerType", nonNull(customerType) ? customerType.getStadsbackenTranslation() : null);
	}

	static Specification<InvoiceEntity> withFacilityIds(List<String> facilityIds) {
		return (invoiceEntity, cq, cb) -> isEmpty(facilityIds) ? cb.and() : addToInStringClause(cb.in(invoiceEntity.get("facilityId")), facilityIds);
	}

	static Specification<InvoiceEntity> withAdministration(String administration) {
		return buildEqualFilter("administration", administration);
	}
	
	static Specification<InvoiceEntity> withOcrNumber(Long ocrNumber) {
		return buildEqualFilter("ocrNumber", ocrNumber);
	}

	static Specification<InvoiceEntity> withInvoiceDate(LocalDate dateFrom, LocalDate dateTo) {
		return buildDateFilter("invoiceDate", dateFrom, dateTo);
	}

	static Specification<InvoiceEntity> withInvoiceName(String invoiceName) {
		return buildEqualFilter("invoiceName", invoiceName);
	}
	
	static Specification<InvoiceEntity> withInvoiceNumber(Long invoiceNumber) {
		return buildEqualFilter("invoiceNumber", invoiceNumber);
	}

	static Specification<InvoiceEntity> withInvoiceType(String invoiceType) {
		return buildEqualFilter("invoiceType", invoiceType);
	}

	static Specification<InvoiceEntity> withInvoiceStatus(String invoiceStatus) {
		return buildEqualFilter("invoiceStatus", invoiceStatus);
	}

	static Specification<InvoiceEntity> withDueDate(LocalDate dateFrom, LocalDate dateTo) {
		return buildDateFilter("dueDate", dateFrom, dateTo);
	}

	static Specification<InvoiceEntity> withOrganizationGroup(String organizationGroup) {
		return buildEqualFilter("organizationGroup", organizationGroup);
	}

	static Specification<InvoiceEntity> withOrganizationId(String organizationId) {
		return buildEqualFilter("organizationId", organizationId);
	}
	
	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * @param attribute name that will be used in filter
	 * @param value value (or null) to compare against
	 * @return Specification<InvoiceEntity> matching sent in comparison
	 */
	private static Specification<InvoiceEntity> buildEqualFilter(String attribute, Object value) {
		return (invoiceEntity, cq, cb) -> 
			nonNull(value) ? cb.equal(invoiceEntity.get(attribute), value) : cb.and();
	}

	/**
	 * Method builds a filter depending on sent in dates. If both values are null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * @param attribute name of attribute (of type date) that will be used in filter
	 * @param dateFrom date from (or null) to compare against 
	 * @param dateTo date tom (or null) to compare against
	 * @return Specification<InvoiceEntity> matching sent in comparison
	 */
	private static Specification<InvoiceEntity> buildDateFilter(String attribute, LocalDate dateFrom, LocalDate dateTo) {
		return (invoiceEntity, cq, cb) -> {
			
			if (nonNull(dateFrom) && nonNull(dateTo) ) {
				return cb.between(invoiceEntity.get(attribute), dateFrom, dateTo);
			} else if (nonNull(dateFrom)) {
				return cb.greaterThanOrEqualTo(invoiceEntity.get(attribute), dateFrom);
			} else if (nonNull(dateTo)) {
				return cb.lessThanOrEqualTo(invoiceEntity.get(attribute), dateTo);
			}
			
			// always-true predicate, meaning that if no dateFrom or to has been set, no filtering will be applied
			return cb.and();
		};
	}
	
	private static In<String> addToInStringClause(In<String> clause, List<String> values) {
		values.stream()
			.forEach(clause::value);

		return clause;
	}

	private static In<Integer> addToInIntegerClause(In<Integer> clause, List<Integer> values) {
		values.stream()
			.forEach(clause::value);

		return clause;
	}
}
