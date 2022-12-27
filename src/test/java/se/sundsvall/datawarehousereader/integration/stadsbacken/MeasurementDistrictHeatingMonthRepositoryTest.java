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

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementDistrictHeatingMonthEntity;

/**
 * MeasurementDistrictHeatingMonth repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class MeasurementDistrictHeatingMonthRepositoryTest {

	@Autowired
	private MeasurementDistrictHeatingMonthRepository repository;

	@Test
	void testResponseForMeasurementOfOneMonth() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109141107350";
		final var dateTimeFrom = LocalDate.of(2017, 9, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				MeasurementDistrictHeatingMonthEntity::getCustomerOrgId,
				MeasurementDistrictHeatingMonthEntity::getFacilityId,
				MeasurementDistrictHeatingMonthEntity::getFeedType,
				MeasurementDistrictHeatingMonthEntity::getFeedTypeId,
				MeasurementDistrictHeatingMonthEntity::getInterpolation,
				MeasurementDistrictHeatingMonthEntity::getMeasurementTimestamp,
				MeasurementDistrictHeatingMonthEntity::getReadingSequence,
				MeasurementDistrictHeatingMonthEntity::getUnit,
				MeasurementDistrictHeatingMonthEntity::getUsage,
				MeasurementDistrictHeatingMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), dateTimeFrom, 534497, "kWh", toBigDecimal(17.33), null));
	}

	@Test
	void testResponseSortedAscendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109141107350";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(4)
			.extracting(
				MeasurementDistrictHeatingMonthEntity::getCustomerOrgId,
				MeasurementDistrictHeatingMonthEntity::getFacilityId,
				MeasurementDistrictHeatingMonthEntity::getFeedType,
				MeasurementDistrictHeatingMonthEntity::getFeedTypeId,
				MeasurementDistrictHeatingMonthEntity::getInterpolation,
				MeasurementDistrictHeatingMonthEntity::getMeasurementTimestamp,
				MeasurementDistrictHeatingMonthEntity::getReadingSequence,
				MeasurementDistrictHeatingMonthEntity::getUnit,
				MeasurementDistrictHeatingMonthEntity::getUsage,
				MeasurementDistrictHeatingMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), 160210, "kWh", toBigDecimal(69.11), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), 257573, "kWh", toBigDecimal(79.93), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), 419679, "kWh", toBigDecimal(69.65), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), 534497, "kWh", toBigDecimal(17.33), null));
	}

	@Test
	void testResponseSortedDescendingOnMeasurementTimestamp() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109141107350";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(0, 100).withSort(by(DESC, "measurementTimestamp")));

		assertThat(page.getContent())
			.hasSize(4)
			.extracting(
				MeasurementDistrictHeatingMonthEntity::getCustomerOrgId,
				MeasurementDistrictHeatingMonthEntity::getFacilityId,
				MeasurementDistrictHeatingMonthEntity::getFeedType,
				MeasurementDistrictHeatingMonthEntity::getFeedTypeId,
				MeasurementDistrictHeatingMonthEntity::getInterpolation,
				MeasurementDistrictHeatingMonthEntity::getMeasurementTimestamp,
				MeasurementDistrictHeatingMonthEntity::getReadingSequence,
				MeasurementDistrictHeatingMonthEntity::getUnit,
				MeasurementDistrictHeatingMonthEntity::getUsage,
				MeasurementDistrictHeatingMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 9, 1).atStartOfDay(), 534497, "kWh", toBigDecimal(17.33), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), 419679, "kWh", toBigDecimal(69.65), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 7, 1).atStartOfDay(), 257573, "kWh", toBigDecimal(79.93), null),
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 6, 1).atStartOfDay(), 160210, "kWh", toBigDecimal(69.11), null));
	}

	@Test
	void testPagingOfResponse() {
		final var customerOrgNbr = "5534567890";
		final var facilityId = "735999109141107350";
		final var dateTimeFrom = LocalDate.of(2017, 6, 1).atStartOfDay();
		final var dateTimeTo = LocalDate.of(2017, 9, 30).atStartOfDay();

		final var page = repository.findAllMatching(customerOrgNbr, facilityId, dateTimeFrom, dateTimeTo, PageRequest.of(2, 1).withSort(by(ASC, "measurementTimestamp")));

		assertThat(page.getNumber()).isEqualTo(2);
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(4);
		assertThat(page.getTotalElements()).isEqualTo(4);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				MeasurementDistrictHeatingMonthEntity::getCustomerOrgId,
				MeasurementDistrictHeatingMonthEntity::getFacilityId,
				MeasurementDistrictHeatingMonthEntity::getFeedType,
				MeasurementDistrictHeatingMonthEntity::getFeedTypeId,
				MeasurementDistrictHeatingMonthEntity::getInterpolation,
				MeasurementDistrictHeatingMonthEntity::getMeasurementTimestamp,
				MeasurementDistrictHeatingMonthEntity::getReadingSequence,
				MeasurementDistrictHeatingMonthEntity::getUnit,
				MeasurementDistrictHeatingMonthEntity::getUsage,
				MeasurementDistrictHeatingMonthEntity::getUuid)
			.containsExactly(
				tuple("5534567890", "735999109141107350", "Aktiv", Integer.valueOf(0), Integer.valueOf(0), LocalDate.of(2017, 8, 1).atStartOfDay(), 419679, "kWh", toBigDecimal(69.65), null));
	}

	@Test
	void testResponseWithHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2013, 9, 1).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsBeforeDate() {
		final var dateTimeTo = LocalDate.of(2013, 8, 31).atStartOfDay();

		final var page = repository.findAllMatching(null, null, null, dateTimeTo, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
	}

	@Test
	void testResponseWithHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 11, 06).atStartOfDay();

		final var page = repository.findAllMatching(null, null, dateTimeFrom, null, PageRequest.of(0, 100));

		assertThat(page.getNumberOfElements()).isEqualTo(3);
		assertThat(page.getNumber()).isZero();
		assertThat(page.getTotalElements()).isEqualTo(3);
		assertThat(page.getTotalPages()).isEqualTo(1);
	}

	@Test
	void testResponseWithNoHitsAfterDate() {
		final var dateTimeFrom = LocalDate.of(2019, 11, 07).atStartOfDay();

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
		assertThat(page.getTotalElements()).isEqualTo(596);
		assertThat(page.getTotalPages()).isEqualTo(6); // A total of 596 entries divided in 100 equals 6 pages
	}

	private static BigDecimal toBigDecimal(double value) {
		return BigDecimal.valueOf(value).setScale(10);
	}

}
