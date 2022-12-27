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

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityDayEntity;

/**
 * MeasurementElectricityMonth repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class MeasurementElectricityDayRepositoryTest {

	@Autowired
	private MeasurementElectricityDayRepository repository;

	@Test
	void testResponseMeasurementsForOneDay() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112501170";
		final var dateTimeFrom = LocalDate.of(2019, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 6, 1).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getContent())
			.hasSize(5)
			.extracting(
				MeasurementElectricityDayEntity::getCustomerOrgId,
				MeasurementElectricityDayEntity::getFacilityId,
				MeasurementElectricityDayEntity::getFeedType,
				MeasurementElectricityDayEntity::getInterpolation,
				MeasurementElectricityDayEntity::getMeasurementTimestamp,
				MeasurementElectricityDayEntity::getUnit,
				MeasurementElectricityDayEntity::getUsage,
				MeasurementElectricityDayEntity::getUuid)
			.containsExactlyInAnyOrder(
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(0.11), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(1), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(12), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), dateTimeFrom, "MWh", toBigDecimal(19), null));
	}

	@Test
	void testResponseSortedAscendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112501170";
		final var dateTimeFrom = LocalDate.of(2019, 7, 3).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 7, 6).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(9)
			.extracting(
				MeasurementElectricityDayEntity::getCustomerOrgId,
				MeasurementElectricityDayEntity::getFacilityId,
				MeasurementElectricityDayEntity::getFeedType,
				MeasurementElectricityDayEntity::getInterpolation,
				MeasurementElectricityDayEntity::getMeasurementTimestamp,
				MeasurementElectricityDayEntity::getUnit,
				MeasurementElectricityDayEntity::getUsage,
				MeasurementElectricityDayEntity::getUuid)
			.containsExactly(
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(0.03), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(2.25), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(0.04), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(2.25), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 5).atStartOfDay(), "MWh", toBigDecimal(0.05), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 6).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 6).atStartOfDay(), "MWh", toBigDecimal(0.07), null));
	}

	@Test
	void testResponseSortedDescendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112501170";
		final var dateTimeFrom = LocalDate.of(2019, 7, 3).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 7, 6).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(DESC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(9)
			.extracting(
				MeasurementElectricityDayEntity::getCustomerOrgId,
				MeasurementElectricityDayEntity::getFacilityId,
				MeasurementElectricityDayEntity::getFeedType,
				MeasurementElectricityDayEntity::getInterpolation,
				MeasurementElectricityDayEntity::getMeasurementTimestamp,
				MeasurementElectricityDayEntity::getUnit,
				MeasurementElectricityDayEntity::getUsage,
				MeasurementElectricityDayEntity::getUuid)
			.containsExactly(
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 6).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 6).atStartOfDay(), "MWh", toBigDecimal(0.07), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 5).atStartOfDay(), "MWh", toBigDecimal(0.05), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(0.04), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(2.25), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(0), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(0.03), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(2.25), null));
	}

	@Test
	void testPagingOfResponse() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109112501170";
		final var dateTimeFrom = LocalDate.of(2019, 7, 3).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2019, 7, 4).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(1, 2).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getNumber()).isEqualTo(1);
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(3);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getContent())
			.hasSize(2)
			.extracting(
				MeasurementElectricityDayEntity::getCustomerOrgId,
				MeasurementElectricityDayEntity::getFacilityId,
				MeasurementElectricityDayEntity::getFeedType,
				MeasurementElectricityDayEntity::getInterpolation,
				MeasurementElectricityDayEntity::getMeasurementTimestamp,
				MeasurementElectricityDayEntity::getUnit,
				MeasurementElectricityDayEntity::getUsage,
				MeasurementElectricityDayEntity::getUuid)
			.containsExactly(
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 3).atStartOfDay(), "MWh", toBigDecimal(2.25), null),
				tuple(customerOrgNbr, facilityId, "Energy", Integer.valueOf(0), LocalDate.of(2019, 7, 4).atStartOfDay(), "MWh", toBigDecimal(0), null));
	}

	@Test
	void testResponseWithHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2019, 6, 1).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2019, 5, 31).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
	}

	@Test
	void testResponseWithHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 8, 2).atStartOfDay();

		final var page = repository.findAllMatching(null, null, dateTimeFrom, null, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 8, 3).atStartOfDay();

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
		assertThat(page.getTotalElements()).isEqualTo(790);
		assertThat(page.getTotalPages()).isEqualTo(8); // A total of 790 entries divided in slices of 100 equals 8 pages
	}

	private static BigDecimal toBigDecimal(double value) {
		return BigDecimal.valueOf(value).setScale(10);
	}
}
