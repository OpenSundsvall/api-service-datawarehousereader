package se.sundsvall.datawarehousereader.api.model.installedbase;

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

class InstalledBaseResponseTest {

	@Test
	void testBean() {
		assertThat(InstalledBaseResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var metaData = MetaData.create();
		final var installedBase = InstalledBaseItem.create();

		final var response = InstalledBaseResponse.create()
			.withMetaData(metaData)
			.withInstalledBase(List.of(installedBase));

		assertThat(response.getMetaData()).isEqualTo(metaData);
		assertThat(response.getInstalledBase()).hasSize(1).contains(installedBase);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InstalledBaseResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new InstalledBaseResponse()).hasAllNullFieldsOrProperties();
	}
}
