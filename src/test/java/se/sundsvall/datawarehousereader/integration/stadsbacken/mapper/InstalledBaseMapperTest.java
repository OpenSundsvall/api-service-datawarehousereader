package se.sundsvall.datawarehousereader.integration.stadsbacken.mapper;

import static java.lang.Integer.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;

class InstalledBaseMapperTest {

	@Test
	void toExampleWithNull() {
		assertThat(InstalledBaseMapper.toExample(null).getProbe()).hasAllNullFieldsOrProperties();
	}

	@Test
	void toExampleWithEmptyParameterBean() {
		assertThat(InstalledBaseMapper.toExample(InstalledBaseParameters.create()).getProbe()).hasAllNullFieldsOrProperties();
	}

	@Test
	void toExampleWithFullparameterBean() {
		final var careOf = "careOf";
		final var city = "city";
		final var company = "company";
		final var customerNumber = "123";
		final var facilityId = "facilityId";
		final var propertyDesignation = "propertyDesignation";
		final var postCode = "postCode";
		final var street = "street";
		final var type = "type";

		final var parameters = InstalledBaseParameters.create();
		parameters.setCareOf(careOf);
		parameters.setCity(city);
		parameters.setCompany(company);
		parameters.setCustomerNumber(customerNumber);
		parameters.setFacilityId(facilityId);
		parameters.setPropertyDesignation(propertyDesignation);
		parameters.setPostCode(postCode);
		parameters.setStreet(street);
		parameters.setType(type);

		final var probe = InstalledBaseMapper.toExample(parameters).getProbe();

		assertThat(probe.getCareOf()).isEqualTo(careOf);
		assertThat(probe.getCity()).isEqualTo(city);
		assertThat(probe.getCompany()).isEqualTo(company);
		assertThat(probe.getCustomerId()).isEqualTo(valueOf(customerNumber));
		assertThat(probe.getHouseName()).isEqualTo(propertyDesignation);
		assertThat(probe.getInternalId()).isNull();
		assertThat(probe.getMetaData()).isNull();
		assertThat(probe.getFacilityId()).isEqualTo(facilityId);
		assertThat(probe.getPostCode()).isEqualTo(postCode);
		assertThat(probe.getStreet()).isEqualTo(street);
		assertThat(probe.getType()).isEqualTo(type);
	}
}
