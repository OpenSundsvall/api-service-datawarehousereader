package se.sundsvall.datawarehousereader.api.model.measurement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class MeasurementMetaDataTest {

	@Test
	void testBean() {
		assertThat(MeasurementMetaData.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		var key = "key";
		var value = "value";

		final var bean = MeasurementMetaData.create()
			.withKey(key)
			.withValue(value);

		assertThat(bean.getKey()).isEqualTo(key);
		assertThat(bean.getValue()).isEqualTo(value);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(MeasurementMetaData.create()).hasAllNullFieldsOrProperties();
		assertThat(new MeasurementMetaData()).hasAllNullFieldsOrProperties();
	}
}
