package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityMonthEntity;

/**
 * MeasurementElectricityMonth repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class MeasurementElectricityMonthRepositoryTest {

	@Autowired
	private MeasurementElectricityMonthRepository repository;

	@Test
	void testResponseSingleMeasurementForOneMonth() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112403108";
		final var dateTimeFrom = LocalDate.of(2019, 7, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 7, 31).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getContent())
			.hasSize(2)
			.extracting(
				MeasurementElectricityMonthEntity::getCustomerOrgId,
				MeasurementElectricityMonthEntity::getFacilityId,
				MeasurementElectricityMonthEntity::getFeedType,
				MeasurementElectricityMonthEntity::getInterpolation,
				MeasurementElectricityMonthEntity::getMeasurementTimestamp,
				MeasurementElectricityMonthEntity::getUnit,
				MeasurementElectricityMonthEntity::getUsage,
				MeasurementElectricityMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), dateTimeFrom, "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), dateTimeFrom, "kWh", toBigDecimal(29.12), null));
	}

	@Test
	void testResponseMultipleMeasurementsForOneMonth() {
		final var customerOrgNbr = "197706010123";
		final var facilityId = "9151530012";
		final var dateTimeFrom = LocalDate.of(2019, 9, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 9, 30).atStartOfDay();
		final var uuid = "B252BC0D-AC49-46A2-B20B-B63DFE9C812B";

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getContent())
			.hasSize(5)
			.extracting(
				MeasurementElectricityMonthEntity::getCustomerOrgId,
				MeasurementElectricityMonthEntity::getFacilityId,
				MeasurementElectricityMonthEntity::getFeedType,
				MeasurementElectricityMonthEntity::getInterpolation,
				MeasurementElectricityMonthEntity::getMeasurementTimestamp,
				MeasurementElectricityMonthEntity::getUnit,
				MeasurementElectricityMonthEntity::getUsage,
				MeasurementElectricityMonthEntity::getUuid)
			.containsExactlyInAnyOrder(
				tuple("197706010123", "9151530012", "Energy", Integer.valueOf(0), dateTimeFrom, "C", toBigDecimal(0), uuid),
				tuple("197706010123", "9151530012", "Energy", Integer.valueOf(0), dateTimeFrom, "kW", toBigDecimal(0), uuid),
				tuple("197706010123", "9151530012", "Energy", Integer.valueOf(0), dateTimeFrom, "m3", toBigDecimal(32.68), uuid),
				tuple("197706010123", "9151530012", "Energy", Integer.valueOf(0), dateTimeFrom, "m3/h", toBigDecimal(0), uuid),
				tuple("197706010123", "9151530012", "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(1.005), uuid));
	}

	@Test
	void testResponseSortedAscendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112403108";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(8)
			.extracting(
				MeasurementElectricityMonthEntity::getCustomerOrgId,
				MeasurementElectricityMonthEntity::getFacilityId,
				MeasurementElectricityMonthEntity::getFeedType,
				MeasurementElectricityMonthEntity::getInterpolation,
				MeasurementElectricityMonthEntity::getMeasurementTimestamp,
				MeasurementElectricityMonthEntity::getUnit,
				MeasurementElectricityMonthEntity::getUsage,
				MeasurementElectricityMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), "kWh", toBigDecimal(11.75), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), "kWh", toBigDecimal(3.83), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(12.92), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), "kWh", toBigDecimal(13.91), null));
	}

	@Test
	void testResponseSortedDescendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112403108";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(DESC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(8)
			.extracting(
				MeasurementElectricityMonthEntity::getCustomerOrgId,
				MeasurementElectricityMonthEntity::getFacilityId,
				MeasurementElectricityMonthEntity::getFeedType,
				MeasurementElectricityMonthEntity::getInterpolation,
				MeasurementElectricityMonthEntity::getMeasurementTimestamp,
				MeasurementElectricityMonthEntity::getUnit,
				MeasurementElectricityMonthEntity::getUsage,
				MeasurementElectricityMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), "kWh", toBigDecimal(13.91), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(12.92), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), "kWh", toBigDecimal(3.83), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), "kWh", toBigDecimal(11.75), null));
	}

	@Test
	void testPagingOfResponse() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112403108";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(2, 2).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getNumber()).isEqualTo(2);
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(4);
		assertThat(page.getTotalElements()).isEqualTo(8);
		assertThat(page.getContent())
			.hasSize(2)
			.extracting(
				MeasurementElectricityMonthEntity::getCustomerOrgId,
				MeasurementElectricityMonthEntity::getFacilityId,
				MeasurementElectricityMonthEntity::getFeedType,
				MeasurementElectricityMonthEntity::getInterpolation,
				MeasurementElectricityMonthEntity::getMeasurementTimestamp,
				MeasurementElectricityMonthEntity::getUnit,
				MeasurementElectricityMonthEntity::getUsage,
				MeasurementElectricityMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(0), null),
				tuple("5534567890", "735999109112403108", "Energy", Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), "kWh", toBigDecimal(12.92), null));
	}

	@Test
	void testResponseWithHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2016, 1, 1).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(7);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(7);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2015, 12, 31).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
	}

	@Test
	void testResponseWithHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 11, 01).atStartOfDay();

		final var page = repository.findAllMatching(null, null, dateTimeFrom, null, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(9);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(9);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 11, 02).atStartOfDay();

		final var page = repository.findAllMatching(null, null, dateTimeFrom, null, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
	}

	@Test
	void testResponseWithNoFiltering() {
		final var page = repository.findAllMatching(null, null, null, null, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(100);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(408);
		assertThat(page.getTotalPages()).isEqualTo(5); // A total of 408 entries divided in slices of 100 equals 5 pages
	}

	private static BigDecimal toBigDecimal(double value) {
		return BigDecimal.valueOf(value).setScale(10);
	}
}
