package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.MeasurementElectricityMonthSpecification.withCustomerOrgId;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.MeasurementElectricityMonthSpecification.withMeasurementTimestamp;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.MeasurementElectricityMonthSpecification.withfacilityId;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityKey;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityMonthEntity;

@Transactional
@CircuitBreaker(name = "measurementElectricityMonthRepository")
public interface MeasurementElectricityMonthRepository 
	extends PagingAndSortingRepository<MeasurementElectricityMonthEntity, MeasurementElectricityKey>, JpaSpecificationExecutor<MeasurementElectricityMonthEntity> {
	
	default Page<MeasurementElectricityMonthEntity> findAllMatching(String customerOrgNumber, String facilityId, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Pageable pageable) {
		return this.findAll(
			withCustomerOrgId(customerOrgNumber).
				and(withfacilityId(facilityId)).
				and(withMeasurementTimestamp(dateTimeFrom, dateTimeTo))
			, pageable);
	}
}
