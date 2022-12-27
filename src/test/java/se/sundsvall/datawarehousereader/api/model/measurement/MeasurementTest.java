package se.sundsvall.datawarehousereader.api.model.measurement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.Category;

class MeasurementTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), OffsetDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(Measurement.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		var aggregatedOn = Aggregation.HOUR;
		var category = Category.WASTE_MANAGEMENT;
		var facilityId = "facilityId";
		var interpolation = 123;
		var measurementType = "measurementType";
		var metaData = List.of(MeasurementMetaData.create());
		var partyId = "partyId";
		var timeStamp = OffsetDateTime.now();
		var unit = "unit";
		var value = BigDecimal.TEN;

		final var bean = Measurement.create()
			.withAggregatedOn(aggregatedOn)
			.withCategory(category)
			.withFacilityId(facilityId)
			.withInterpolation(interpolation)
			.withMeasurementType(measurementType)
			.withMetaData(metaData)
			.withPartyId(partyId)
			.withTimestamp(timeStamp)
			.withUnit(unit)
			.withValue(value);

		assertThat(bean.getAggregatedOn()).isEqualTo(aggregatedOn);
		assertThat(bean.getCategory()).isEqualTo(category);
		assertThat(bean.getFacilityId()).isEqualTo(facilityId);
		assertThat(bean.getInterpolation()).isEqualTo(interpolation);
		assertThat(bean.getMeasurementType()).isEqualTo(measurementType);
		assertThat(bean.getMetaData()).isEqualTo(metaData);
		assertThat(bean.getPartyId()).isEqualTo(partyId);
		assertThat(bean.getTimestamp()).isEqualTo(timeStamp);
		assertThat(bean.getUnit()).isEqualTo(unit);
		assertThat(bean.getValue()).isEqualTo(value);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Measurement.create()).hasAllNullFieldsOrPropertiesExcept("interpolation").hasFieldOrPropertyWithValue("interpolation", 0);
		assertThat(new Measurement()).hasAllNullFieldsOrPropertiesExcept("interpolation").hasFieldOrPropertyWithValue("interpolation", 0);
	}
}
