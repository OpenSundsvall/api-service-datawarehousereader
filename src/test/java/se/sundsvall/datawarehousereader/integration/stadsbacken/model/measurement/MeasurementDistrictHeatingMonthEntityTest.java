package se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class MeasurementDistrictHeatingMonthEntityTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDateTime.now().plusSeconds(new Random().nextInt()), LocalDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(MeasurementDistrictHeatingMonthEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var customerOrgId = "customerOrgId";
		final var facilityId = "facilityId";
		final var feedType = "feedType";
		final var feedTypeId = Integer.valueOf(012345);
		final var interpolation = Integer.valueOf(543210);
		final var measurementTimestamp = LocalDateTime.now();
		final var readingSequence = Integer.valueOf(223344);
		final var unit = "unit";
		final var usage = BigDecimal.valueOf(123.456);
		final var uuid = "uuid";

		final var entity = MeasurementDistrictHeatingMonthEntity.create()
			.withCustomerOrgId(customerOrgId)
			.withFacilityId(facilityId)
			.withFeedType(feedType)
			.withFeedTypeId(feedTypeId)
			.withInterpolation(interpolation)
			.withMeasurementTimestamp(measurementTimestamp)
			.withReadingSequence(readingSequence)
			.withUnit(unit)
			.withUsage(usage)
			.withUuid(uuid);

		assertThat(entity.getCustomerOrgId()).isEqualTo(customerOrgId);
		assertThat(entity.getFacilityId()).isEqualTo(facilityId);
		assertThat(entity.getFeedType()).isEqualTo(feedType);
		assertThat(entity.getFeedTypeId()).isEqualTo(feedTypeId);
		assertThat(entity.getInterpolation()).isEqualTo(interpolation);
		assertThat(entity.getMeasurementTimestamp()).isEqualTo(measurementTimestamp);
		assertThat(entity.getReadingSequence()).isEqualTo(readingSequence);
		assertThat(entity.getUnit()).isEqualTo(unit);
		assertThat(entity.getUsage()).isEqualTo(usage);
		assertThat(entity.getUuid()).isEqualTo(uuid);
		assertThat(entity.getCustomerOrgId()).isEqualTo(customerOrgId);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(MeasurementDistrictHeatingMonthEntity.create()).hasAllNullFieldsOrProperties();
		assertThat(new MeasurementDistrictHeatingMonthEntity()).hasAllNullFieldsOrProperties();
	}
}
