package se.sundsvall.datawarehousereader.integration.stadsbacken.specification;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementDistrictHeatingMonthEntity;

public interface MeasurementDistrictHeatingMonthSpecification {

	static Specification<MeasurementDistrictHeatingMonthEntity> withCustomerOrgId(String customerOrgId) {
		return buildEqualFilter("customerOrgId", customerOrgId);
	}

	static Specification<MeasurementDistrictHeatingMonthEntity> withfacilityId(String facilityId) {
		return buildEqualFilter("facilityId", facilityId);
	}

	static Specification<MeasurementDistrictHeatingMonthEntity> withMeasurementTimestamp(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
		return buildDateFilter("measurementTimestamp", dateTimeFrom, dateTimeTo);
	}

	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * 
	 * @param attribute name that will be used in filter
	 * @param value     value (or null) to compare against
	 * @return Specification<MeasurementDistrictHeatingMonthEntity> matching sent in comparison
	 */
	private static Specification<MeasurementDistrictHeatingMonthEntity> buildEqualFilter(String attribute, Object value) {
		return (measurementDistrictHeatingMonthEntity, cq, cb) -> nonNull(value) ? cb.equal(measurementDistrictHeatingMonthEntity.get(attribute), value) : cb.and();
	}

	/**
	 * Method builds a filter depending on sent in time stamps. If both values are null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 * 
	 * @param attribute    name of attribute (of type date) that will be used in filter
	 * @param dateTimeFrom date from (or null) to compare against
	 * @param dateTimeTo   date tom (or null) to compare against
	 * @return Specification<MeasurementDistrictHeatingMonthEntity> matching sent in comparison
	 */
	private static Specification<MeasurementDistrictHeatingMonthEntity> buildDateFilter(String attribute, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
		return (measurementDistrictHeatingMonthEntity, cq, cb) -> {
			
			if (nonNull(dateTimeFrom) && nonNull(dateTimeTo) ) {
				return cb.between(measurementDistrictHeatingMonthEntity.get(attribute), dateTimeFrom, dateTimeTo);
			} else if (nonNull(dateTimeFrom)) {
				return cb.greaterThanOrEqualTo(measurementDistrictHeatingMonthEntity.get(attribute), dateTimeFrom);
			} else if (nonNull(dateTimeTo)) {
				return cb.lessThanOrEqualTo(measurementDistrictHeatingMonthEntity.get(attribute), dateTimeTo);
			}
      
			// always-true predicate, meaning that if no dateFrom or to has been set, no filtering will be applied
			return cb.and();
		};
	}
}
