package se.sundsvall.datawarehousereader.api.model.measurement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.MetaData;

class MeasurementResponseTest {

	@Test
	void testBean() {
		assertThat(MeasurementResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var metaData = MetaData.create();
		final var measurement = Measurement.create();

		final var response = MeasurementResponse.create()
			.withMetaData(metaData)
			.withMeasurements(List.of(measurement));

		assertThat(response.getMetaData()).isEqualTo(metaData);
		assertThat(response.getMeasurements()).hasSize(1).contains(measurement);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(MeasurementResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new MeasurementResponse()).hasAllNullFieldsOrProperties();
	}
}
