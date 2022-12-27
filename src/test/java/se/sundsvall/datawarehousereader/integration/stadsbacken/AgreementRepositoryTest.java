package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_HEATING;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;

/**
 * Agreement repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class AgreementRepositoryTest {

	@Autowired
	private AgreementRepository repository;

	@Test
	void getAgreementNoMatch() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withAgreementId("99999").withFacilityId("1112222").withBillingId("222333"), null,
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getContent()).isEmpty();
	}

	@Test
	void getAgreementsByCategory() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withCategory(List.of(DISTRICT_HEATING)), null,
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(13);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(13);
		assertThat(page.getContent())
			.hasSize(13)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactlyInAnyOrder(
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAE1", "198609234567", 633134, "1108", "Fjärrvärme", 2046960, 41903, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 2, 8, 0, 0), LocalDateTime.of(2019, 8, 31, 0, 0)),
				tuple("336EC35A-3335-4FA3-B792-60061222B0E9", "193807289012", 632096, "2455", "Fjärrvärme", 2045420, 40798, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 10, 4, 0, 0), null),
				tuple(null, "5568001234", 632258, "1396", "Fjärrvärme", 2045960, 41136, "Effektabonnemang lågspänning", "true", "false", null, LocalDateTime.of(2016, 10, 17, 0, 0), null),
				tuple("15F2DC01-B351-4AFC-99C1-596BD7D404C6", "198402012346", 632752, "1277", "Fjärrvärme", 2046361, 41453, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 12, 1, 0, 0), null),
				tuple("53A17B25-0E65-487A-A435-ED52809C386C", "193702123456", 632819, "1981", "Fjärrvärme", 2046470, 41545, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 1, 1, 0, 0), null),
				tuple("2412C4EB-63EB-4FEC-B8D2-14783E6508A1", "199505023456", 632947, "1773", "Fjärrvärme", 2046674, 41719, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 1, 12, 0, 0), null),
				tuple("336EC35A-3335-4FA3-B792-60061222B0E9", "198905089012", 632067, "2392", "Fjärrvärme", 2045390, 40768, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 6, 7, 0, 0), null),
				tuple("E575A56F-A0A1-45EF-8123-C95A614F31F4", "198709290123", 632205, "1209", "Fjärrvärme", 2047337, 42216, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 3, 21, 0, 0), null),
				tuple("BECE12C4-AE4B-4826-8C6C-514009FF5C94", "197007912345", 633360, "2339", "Fjärrvärme", 2047361, 42234, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 3, 25, 0, 0), LocalDateTime.of(2019, 7, 31, 0, 0)),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 630024, "1539", "Fjärrvärme", 2048465, 43176, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 8, 16, 0, 0), null),
				tuple("BECE12C4-AE4B-4826-8C6C-514009FF5C94", "197007912345", 634100, "1448", "Fjärrvärme", 2048522, 43225, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 9, 1, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAE1", "198609234567", 644333, "3062", "Fjärrvärme", 2049933, 44364, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 1, 16, 0, 0), null),
				tuple("745CBD9A-B213-47FE-B25A-6F1AD391F4B6", "195409067890", 666334, "3819", "Fjärrvärme", 2058855, 53286, "Effektabonnemang lågspänning", "true", "false", null, LocalDateTime.of(1950, 1, 1, 0, 0), null));
	}

	@Test
	void getAgreementByPartyIdAndMultipleCategories() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withCategory(List.of(DISTRICT_HEATING, ELECTRICITY)), "199205123456",
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(9);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(9);
		assertThat(page.getContent())
			.hasSize(9)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactlyInAnyOrder(
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 632872, "735999109170403027", "El", 2046560, 41622, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 1, 1, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 632563, "735999109232609053", "El", 2046012, 41185, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 10, 25, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 633700, "735999109453841010", "El", 2047888, 42683, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 6, 1, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 632912, "735999109211809887", "El", 2046622, 41678, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 1, 4, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 648730, "735999109116015154", "El", 2051611, 46042, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(1950, 1, 1, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 630024, "1539", "Fjärrvärme", 2048465, 43176, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 8, 16, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 632628, "735999109271319081", "El", 2046111, 41255, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 11, 2, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 632505, "735999109114705002", "El", 2045932, 41111, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 10, 14, 0, 0), null),
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAFF", "199205123456", 633151, "735999109162107889", "El", 2046994, 41928, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 3, 1, 0, 0), null));
	}

	@Test
	void getAgreementByAgreeementId() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withAgreementId("41426"), null,
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactly(
				tuple("B94F5BC6-7D29-443B-A055-C66851D3FD36", "198402012345", 632737, "735999109450512012", "El", 2046329, 41426, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 11, 29, 0, 0, 0), LocalDateTime.of(2019, 9, 3, 0, 0,
					0)));
	}

	@Test
	void getAgreementsByCategoryPageOneOfThirteen() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withCategory(List.of(DISTRICT_HEATING)), null,
			PageRequest.of(0, 1));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(13);
		assertThat(page.getTotalElements()).isEqualTo(13);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactly(
				tuple("336EC35A-3335-4FA3-B792-60061222B0E9", "198905089012", 632067, "2392", "Fjärrvärme", 2045390, 40768, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 6, 7, 0, 0), null));
	}

	@Test
	void getAgreementsByCategoryAndFacilityId() {
		final var page = repository.findAllByParameters(AgreementParameters.create().withCategory(List.of(DISTRICT_HEATING)).withFacilityId("1108"), null,
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactly(
				tuple("65E78C52-B2E1-42DD-8D42-828A94E5BAE1", "198609234567", 633134, "1108", "Fjärrvärme", 2046960, 41903, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2017, 2, 8, 0, 0), LocalDateTime.of(2019, 8, 31, 0, 0)));
	}

	@Test
	void getAgreementByCustomerOrgIdAndNullInParameters() {
		final var page = repository.findAllByParameters(null, "198402012345",
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactly(
				tuple("B94F5BC6-7D29-443B-A055-C66851D3FD36", "198402012345", 632737, "735999109450512012", "El", 2046329, 41426, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 11, 29, 0, 0, 0), LocalDateTime.of(2019, 9, 3, 0, 0,
					0)));
	}

	@Test
	void getAgreementByCustomerOrgId() {
		final var page = repository.findAllByParameters(AgreementParameters.create(), "198402012345",
			PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(AgreementEntity::getUuid,
				AgreementEntity::getCustomerOrgId,
				AgreementEntity::getCustomerId,
				AgreementEntity::getFacilityId,
				AgreementEntity::getCategory,
				AgreementEntity::getBillingId,
				AgreementEntity::getAgreementId,
				AgreementEntity::getDescription,
				AgreementEntity::getMainAgreement,
				AgreementEntity::getBinding,
				AgreementEntity::getBindingRule,
				AgreementEntity::getFromDate,
				AgreementEntity::getToDate)
			.containsExactly(
				tuple("B94F5BC6-7D29-443B-A055-C66851D3FD36", "198402012345", 632737, "735999109450512012", "El", 2046329, 41426, "Fastpris 2 år", "true", "false", null, LocalDateTime.of(2016, 11, 29, 0, 0, 0), LocalDateTime.of(2019, 9, 3, 0, 0,
					0)));
	}
}
