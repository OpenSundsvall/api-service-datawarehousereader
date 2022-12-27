package se.sundsvall.datawarehousereader.integration.stadsbacken.specification;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityMonthEntity;

public interface MeasurementElectricityMonthSpecification {

	static Specification<MeasurementElectricityMonthEntity> withCustomerOrgId(String customerOrgId) {
		return buildEqualFilter("customerOrgId", customerOrgId);
	}
	
	static Specification<MeasurementElectricityMonthEntity> withfacilityId(String facilityId) {
		return buildEqualFilter("facilityId", facilityId);
	}

	static Specification<MeasurementElectricityMonthEntity> withMeasurementTimestamp(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
		return buildDateFilter("measurementTimestamp", dateTimeFrom, dateTimeTo);
	}

	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * @param attribute name that will be used in filter
	 * @param value value (or null) to compare against
	 * @return Specification<MeasurementElectricityMonthEntity> matching sent in comparison
	 */
	private static Specification<MeasurementElectricityMonthEntity> buildEqualFilter(String attribute, Object value) {
		return (measurementElectricityMonthEntity, cq, cb) -> 
			nonNull(value) ? cb.equal(measurementElectricityMonthEntity.get(attribute), value) : cb.and();
	}

	/**
	 * Method builds a filter depending on sent in time stamps. If both values are null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * @param attribute name of attribute (of type date) that will be used in filter
	 * @param dateTimeFrom date from (or null) to compare against 
	 * @param dateTimeTo date tom (or null) to compare against
	 * @return Specification<MeasurementElectricityMonthEntity> matching sent in comparison
	 */
	private static Specification<MeasurementElectricityMonthEntity> buildDateFilter(String attribute, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
		return (measurementElectricityMonthEntity, cq, cb) -> {
			
			if (nonNull(dateTimeFrom) && nonNull(dateTimeTo) ) {
				return cb.between(measurementElectricityMonthEntity.get(attribute), dateTimeFrom, dateTimeTo);
			} else if (nonNull(dateTimeFrom)) {
				return cb.greaterThanOrEqualTo(measurementElectricityMonthEntity.get(attribute), dateTimeFrom);
			} else if (nonNull(dateTimeTo)) {
				return cb.lessThanOrEqualTo(measurementElectricityMonthEntity.get(attribute), dateTimeTo);
			}
			
			// always-true predicate, meaning that if no dateFrom or to has been set, no filtering will be applied
			return cb.and();
		};
	}
}
