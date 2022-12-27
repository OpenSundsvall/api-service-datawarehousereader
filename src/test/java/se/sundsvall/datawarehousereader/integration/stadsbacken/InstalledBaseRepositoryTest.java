package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.mapper.InstalledBaseMapper.toExample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemMetaDataEmbeddable;

/**
 * Installed base repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class InstalledBaseRepositoryTest {

	@Autowired
	private InstalledBaseRepository repository;

	@Test
	void getInstalledBaseNoMatch() {
		final var page = repository.findAll(toExample(createParameters("99999", null, null)), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getContent()).isEmpty();
	}

	@Test
	void getInstalledBaseNoFilters() {
		final var page = repository.findAll(toExample(InstalledBaseParameters.create()), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(100);
		assertThat(page.getTotalPages()).isEqualTo(4);
		assertThat(page.getTotalElements()).isEqualTo(322);
		assertThat(page.getContent()).hasSize(100);
	}

	@Test
	void getInstalledBaseByCustomerId() {
		final var page = repository.findAll(toExample(createParameters("38308", null, null)), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
			.hasSize(2)
			.extracting(
				InstalledBaseItemEntity::getCareOf,
				InstalledBaseItemEntity::getCompany,
				InstalledBaseItemEntity::getCustomerId,
				InstalledBaseItemEntity::getInternalId,
				InstalledBaseItemEntity::getFacilityId,
				InstalledBaseItemEntity::getStreet,
				InstalledBaseItemEntity::getType)
			.containsExactlyInAnyOrder(
				tuple("Privat företag AB", "Sundsvall Energi AB", 38308, 1886, "9412001019", "Gatan 66", "Elhandel"),
				tuple("Privat företag AB", "Sundsvall Energi AB", 38308, 498, "38308", "", "Avfallsvåg"));

		assertThat(page.getContent().get(0).getMetaData()).isEmpty();
		assertThat(page.getContent().get(1).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Sollentuna"));
	}

	@Test
	void getInstalledBaseByCustomerIdPageOneOfTwo() {
		final var page = repository.findAll(toExample(createParameters("38308", null, null)), PageRequest.of(0, 1));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InstalledBaseItemEntity::getCareOf,
				InstalledBaseItemEntity::getCompany,
				InstalledBaseItemEntity::getCustomerId,
				InstalledBaseItemEntity::getHouseName,
				InstalledBaseItemEntity::getInternalId,
				InstalledBaseItemEntity::getFacilityId,
				InstalledBaseItemEntity::getType)
			.containsExactly(tuple("Privat företag AB", "Sundsvall Energi AB", 38308, null, 498, "38308", "Avfallsvåg"));

		assertThat(page.getContent().get(0).getMetaData()).isEmpty();
	}

	@Test
	void getInstalledBaseByCustomerIdAndCompany() {
		final var page = repository.findAll(toExample(createParameters("600606", "Sundsvall Energi AB", null)), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(4);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(4);
		assertThat(page.getContent())
			.hasSize(4)
			.extracting(
				InstalledBaseItemEntity::getCareOf,
				InstalledBaseItemEntity::getCity,
				InstalledBaseItemEntity::getCompany,
				InstalledBaseItemEntity::getCustomerId,
				InstalledBaseItemEntity::getInternalId,
				InstalledBaseItemEntity::getFacilityId,
				InstalledBaseItemEntity::getPostCode,
				InstalledBaseItemEntity::getStreet,
				InstalledBaseItemEntity::getType)
			.containsExactlyInAnyOrder(
				tuple("Fräscha fastigheter AB", null, "Sundsvall Energi AB", 600606, 3619, "9120601050", null, "Gatan 3", "Elhandel"),
				tuple("Fräscha fastigheter AB", null, "Sundsvall Energi AB", 600606, 17255, "735999109140402043", null, "Gatan 14", "Elhandel"),
				tuple("Fräscha fastigheter AB", "SUNDSVALL", "Sundsvall Energi AB", 600606, 3620, "9140205031", "85350", "Gatan 10", "Elhandel"),
				tuple("Fräscha fastigheter AB", "SUNDSVALL", "Sundsvall Energi AB", 600606, 3621, "9140205056", "85350", "Gatan 14", "Elhandel"));

		assertThat(page.getContent().get(0).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Täby"));
		assertThat(page.getContent().get(1).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Täby"));
		assertThat(page.getContent().get(2).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Täby"));
		assertThat(page.getContent().get(3).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Halmstad"));
	}

	@Test
	void getInstalledBaseByFacilityId() {
		final var page = repository.findAll(toExample(createParameters(null, null, "9111003092")), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InstalledBaseItemEntity::getCareOf,
				InstalledBaseItemEntity::getCompany,
				InstalledBaseItemEntity::getCustomerId,
				InstalledBaseItemEntity::getHouseName,
				InstalledBaseItemEntity::getInternalId,
				InstalledBaseItemEntity::getFacilityId,
				InstalledBaseItemEntity::getStreet,
				InstalledBaseItemEntity::getType)
			.containsExactly(tuple("Fastighetsförmedling AB", "Sundsvall Energi AB", 10335, "Ankeborg 2", 2658, "9111003092", "Gatan 27", "Elhandel"));

		assertThat(page.getContent().get(0).getMetaData()).containsExactly(createMetaDataPost("Sundsvall Energi AB", "Nätområde", "netarea", "location", "Malmö-Burlöv"));
	}

	private static InstalledBaseParameters createParameters(String customerNumber, String company, String facilityId) {
		InstalledBaseParameters parameters = InstalledBaseParameters.create();
		parameters.setCustomerNumber(customerNumber);
		parameters.setCompany(company);
		parameters.setFacilityId(facilityId);
		return parameters;
	}

	private static InstalledBaseItemMetaDataEmbeddable createMetaDataPost(String company, String displayName, String key, String type, String value) {
		InstalledBaseItemMetaDataEmbeddable metadata = new InstalledBaseItemMetaDataEmbeddable();
		metadata.setCompany(company);
		metadata.setDisplayName(displayName);
		metadata.setKey(key);
		metadata.setType(type);
		metadata.setValue(value);
		return metadata;
	}
}
