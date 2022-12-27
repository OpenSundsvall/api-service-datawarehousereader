package se.sundsvall.datawarehousereader.api.model.installedbase;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class InstalledBaseItemTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(InstalledBaseItem.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var careOf = "careOf";
		final var city = "city";
		final var company = "company";
		final var customerNumber = "101";
		final var dateFrom = LocalDate.MIN;
		final var dateTo = LocalDate.MAX;
		final var facilityId = "facilityId";
		final var metaData = List.of(InstalledBaseItemMetaData.create());
		final var postCode = "postCode";
		final var propertyDesignation = "propertyDesignation";
		final var street = "street";
		final var type = "type";

		final var installedBase = InstalledBaseItem.create()
			.withCareOf(careOf)
			.withCity(city)
			.withCompany(company)
			.withCustomerNumber(customerNumber)
			.withDateFrom(dateFrom)
			.withDateTo(dateTo)
			.withFacilityId(facilityId)
			.withMetaData(metaData)
			.withPostCode(postCode)
			.withPropertyDesignation(propertyDesignation)
			.withStreet(street)
			.withType(type);

		assertThat(installedBase.getCareOf()).isEqualTo(careOf);
		assertThat(installedBase.getCity()).isEqualTo(city);
		assertThat(installedBase.getCompany()).isEqualTo(company);
		assertThat(installedBase.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(installedBase.getDateFrom()).isEqualTo(dateFrom);
		assertThat(installedBase.getDateTo()).isEqualTo(dateTo);
		assertThat(installedBase.getFacilityId()).isEqualTo(facilityId);
		assertThat(installedBase.getMetaData()).isEqualTo(metaData);
		assertThat(installedBase.getPostCode()).isEqualTo(postCode);
		assertThat(installedBase.getPropertyDesignation()).isEqualTo(propertyDesignation);
		assertThat(installedBase.getStreet()).isEqualTo(street);
		assertThat(installedBase.getType()).isEqualTo(type);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InstalledBaseItem.create())
			.hasAllNullFieldsOrPropertiesExcept("placementId")
			.hasFieldOrPropertyWithValue("placementId", 0);

		assertThat(new InstalledBaseItem())
			.hasAllNullFieldsOrPropertiesExcept("placementId")
			.hasFieldOrPropertyWithValue("placementId", 0);
	}
}
