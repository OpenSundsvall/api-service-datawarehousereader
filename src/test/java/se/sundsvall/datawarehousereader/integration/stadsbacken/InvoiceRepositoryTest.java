package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;

/**
 * invoice repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class InvoiceRepositoryTest {

	@Autowired
	private InvoiceRepository repository;

	@Test
	void getInvoiceNoMatch() {
		assertThat(repository.findAllByParameters(createParameters("UnknownAdminstrationGroup", null, null, null, null, null, null), PageRequest.of(0, 100))).isEmpty();
	}

	@Test
	void getInvoiceNoFilters() {
		final var page = repository.findAllByParameters(InvoiceParameters.create(), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(100);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.getTotalElements()).isEqualTo(177);
		assertThat(page.getContent()).hasSize(100);
	}

	@Test
	void getInvoiceByAdminstration() {
		final var page = repository.findAllByParameters(createParameters("Sundsvall Energi AB ", null, null, null, null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(9);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(9);
		assertThat(page.getContent())
			.hasSize(9)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1030.06), toBigDecimal(1287.58), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109323726010", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023890.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023999.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(2652.40), toBigDecimal(3315.50), "Statlig instutition", "SUNDSVALL", "sek", 600675, "Enterprise", LocalDate.of(2019, 11, 8), "735999109111805057", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138042593.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(171.03), toBigDecimal(213.79), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109141107350", LocalDate.of(2019, 10, 10),
					"Fjärrvärme", "139034094.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(543.17), toBigDecimal(678.96), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109141713193", LocalDate.of(2019, 10, 10),
					"Fjärrvärme", "139345193.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1937.97), toBigDecimal(2422.46), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109231810016", LocalDate.of(2019, 10,
					10), "Fjärrvärme", "139348890.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(274.96), toBigDecimal(343.7), "Pettson Findus", "Vetlanda", "sek", 691071, "Private", LocalDate.of(2019, 10, 30), "735999226000059909", LocalDate.of(2019, 10, 8), "Fjärrkyla",
					"766763197.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(33.04), toBigDecimal(41.3), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109252711002", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767234891.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(135.55), toBigDecimal(169.44), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109141107350", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767891997.pdf"));
	}

	@Test
	void getInvoiceByAdminstrationAndDueDateBetween() {
		final var page = repository.findAllByParameters(createParameters("Sundsvall Energi AB ", null, LocalDate.of(2019, 10, 30), LocalDate.of(2019, 11, 7), null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(3);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(3);
		assertThat(page.getContent())
			.hasSize(3)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(274.96), toBigDecimal(343.7), "Pettson Findus", "Vetlanda", "sek", 691071, "Private", LocalDate.of(2019, 10, 30), "735999226000059909", LocalDate.of(2019, 10, 8), "Fjärrkyla",
					"766763197.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(33.04), toBigDecimal(41.3), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109252711002", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767234891.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(135.55), toBigDecimal(169.44), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109141107350", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767891997.pdf"));
	}

	@Test
	void getInvoiceByAdminstrationAndDueDateLargerOrEqualThan() {
		final var page = repository.findAllByParameters(createParameters("Sundsvall Energi AB ", null, LocalDate.of(2019, 11, 8), null, null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(6);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getContent())
			.hasSize(6)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1030.06), toBigDecimal(1287.58), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109323726010", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023890.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023999.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(2652.40), toBigDecimal(3315.50), "Statlig instutition", "SUNDSVALL", "sek", 600675, "Enterprise", LocalDate.of(2019, 11, 8), "735999109111805057", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138042593.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(171.03), toBigDecimal(213.79), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109141107350", LocalDate.of(2019, 10, 10),
					"Fjärrvärme", "139034094.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(543.17), toBigDecimal(678.96), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109141713193", LocalDate.of(2019, 10, 10),
					"Fjärrvärme", "139345193.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1937.97), toBigDecimal(2422.46), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109231810016", LocalDate.of(2019, 10,
					10), "Fjärrvärme", "139348890.pdf"));
	}

	@Test
	void getInvoiceByAdminstrationAndDueDateLessOrEqualThan() {
		final var page = repository.findAllByParameters(createParameters("Sundsvall Energi AB ", null, null, LocalDate.of(2019, 11, 7), null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(3);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(3);
		assertThat(page.getContent())
			.hasSize(3)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(274.96), toBigDecimal(343.7), "Pettson Findus", "Vetlanda", "sek", 691071, "Private", LocalDate.of(2019, 10, 30), "735999226000059909", LocalDate.of(2019, 10, 8), "Fjärrkyla",
					"766763197.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(33.04), toBigDecimal(41.3), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109252711002", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767234891.pdf"),
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(135.55), toBigDecimal(169.44), "Fastighetsförmedling AB", "Sundsvall", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 7), "735999109141107350", LocalDate.of(2019, 10, 8),
					"Fjärrvärme", "767891997.pdf"));
	}

	@Test
	void getInvoiceByCustomerId() {
		final var page = repository.findAllByParameters(createParameters(null, List.of("600675"), null, null, null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(2652.40), toBigDecimal(3315.50), "Statlig instutition", "SUNDSVALL", "sek", 600675, "Enterprise", LocalDate.of(2019, 11, 8), "735999109111805057", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138042593.pdf"));
	}

	@Test
	void getInvoiceByMultipleCustomerIds() {
		final var page = repository.findAllByParameters(createParameters(null, List.of("600675", "600606"), null, null, null, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(6);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getContent())
				.hasSize(6)
				.extracting(
						InvoiceEntity::getAdministration,
						InvoiceEntity::getOrganizationId,
						InvoiceEntity::getAmountVatExcluded,
						InvoiceEntity::getAmountVatIncluded,
						InvoiceEntity::getCareOf,
						InvoiceEntity::getCity,
						InvoiceEntity::getCurrency,
						InvoiceEntity::getCustomerId,
						InvoiceEntity::getCustomerType,
						InvoiceEntity::getDueDate,
						InvoiceEntity::getFacilityId,
						InvoiceEntity::getInvoiceDate,
						InvoiceEntity::getInvoiceDescription,
						InvoiceEntity::getInvoiceName)
				.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(2652.40), toBigDecimal(3315.50), "Statlig instutition", "SUNDSVALL", "sek", 600675, "Enterprise", LocalDate.of(2019, 11, 8), "735999109111805057", LocalDate.of(2019, 10, 9),
								"Fjärrvärme", "138042593.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(1085.8900), toBigDecimal(1357.3600), "Fräscha fastigheter AB", "FRÖSÖN", "sek", 600606, "Enterprise", LocalDate.of(2019, 10, 29), "735999109261218707", LocalDate.of(2019, 10, 9),
								"El", "137968194.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(38904.8100), toBigDecimal(48631.0100), "Fräscha fastigheter AB", "FRÖSÖN", "sek", 600606, "Enterprise", LocalDate.of(2019, 10, 29), "735999109140205897", LocalDate.of(2019, 10, 9),
								"El", "137968293.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(9647.9900), toBigDecimal(12059.9900), "Fräscha fastigheter AB", "FRÖSÖN", "sek", 600606, "Enterprise", LocalDate.of(2019, 10, 29), "735999109144630886", LocalDate.of(2019, 10, 9),
								"El", "137968392.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(1520.0000), toBigDecimal(1900.0000), "Fräscha fastigheter AB", "FRÖSÖN", "sek", 600606, "Enterprise", LocalDate.of(2019, 11, 1), "600606", LocalDate.of(2019, 10, 2),
								"El", "765386099.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(-1520.0000), toBigDecimal(-1900.0000), "Fräscha fastigheter AB", "FRÖSÖN", "sek", 600606, "Enterprise", LocalDate.of(2019, 10, 31), "600606", LocalDate.of(2019, 10, 14),
								"El", "767916190.pdf"));
	}

	@Test
	void getInvoiceByInvoiceNumber() {
		final var page = repository.findAllByParameters(createParameters(null, null, null, null, 138023999L, null, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023999.pdf"));
	}

	@Test
	void getInvoiceByOcrNumber() {
		final var page = repository.findAllByParameters(createParameters(null, null, null, null, null, 138023999L, null), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023999.pdf"));
	}

	@Test
	void getInvoiceByFacilityId() {
		final var page = repository.findAllByParameters(createParameters(null, null, null, null, null, null, List.of("735999109324119255")), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(
				InvoiceEntity::getAdministration,
				InvoiceEntity::getOrganizationId,
				InvoiceEntity::getAmountVatExcluded,
				InvoiceEntity::getAmountVatIncluded,
				InvoiceEntity::getCareOf,
				InvoiceEntity::getCity,
				InvoiceEntity::getCurrency,
				InvoiceEntity::getCustomerId,
				InvoiceEntity::getCustomerType,
				InvoiceEntity::getDueDate,
				InvoiceEntity::getFacilityId,
				InvoiceEntity::getInvoiceDate,
				InvoiceEntity::getInvoiceDescription,
				InvoiceEntity::getInvoiceName)
			.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
					"Fjärrvärme", "138023999.pdf"));
	}

	@Test
	void getInvoiceByMultipleFacilityIds() {
		final var page = repository.findAllByParameters(createParameters(null, null, null, null, null, null, List.of("735999109324119255", "735999109451436027")), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
				.hasSize(2)
				.extracting(
						InvoiceEntity::getAdministration,
						InvoiceEntity::getOrganizationId,
						InvoiceEntity::getAmountVatExcluded,
						InvoiceEntity::getAmountVatIncluded,
						InvoiceEntity::getCareOf,
						InvoiceEntity::getCity,
						InvoiceEntity::getCurrency,
						InvoiceEntity::getCustomerId,
						InvoiceEntity::getCustomerType,
						InvoiceEntity::getDueDate,
						InvoiceEntity::getFacilityId,
						InvoiceEntity::getInvoiceDate,
						InvoiceEntity::getInvoiceDescription,
						InvoiceEntity::getInvoiceName)
				.containsExactlyInAnyOrder(
				tuple("Sundsvall Energi AB", "5564786647", toBigDecimal(1306.98), toBigDecimal(1633.73), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 8), "735999109324119255", LocalDate.of(2019, 10, 9),
								"Fjärrvärme", "138023999.pdf"),
				tuple("Sundsvall Elnät", "5565027223", toBigDecimal(1058.2500), toBigDecimal(1322.8100), "Fastighetsförmedling AB", "SUNDSVALL", "sek", 10335, "Enterprise", LocalDate.of(2019, 11, 11), "735999109451436027", LocalDate.of(2019, 10, 10),
								"El", "139349898.pdf"));
	}

	private static InvoiceParameters createParameters(String adminstration, List<String> customerNumber, LocalDate dueDateFrom, LocalDate dueDateTo, Long invoiceNumber, Long ocrNumber, List<String> facilityId) {
		InvoiceParameters parameters = InvoiceParameters.create();
		parameters.setAdministration(adminstration);
		ofNullable(customerNumber).ifPresent(p -> parameters.setCustomerNumber(customerNumber));
		parameters.setDueDateFrom(dueDateFrom);
		parameters.setDueDateTo(dueDateTo);
		parameters.setInvoiceNumber(invoiceNumber);
		parameters.setOcrNumber(ocrNumber);
		ofNullable(facilityId).ifPresent(p -> parameters.setFacilityId(facilityId));

		return parameters;
	}

	private static BigDecimal toBigDecimal(double number) {
		return BigDecimal.valueOf(number).setScale(4);
	}
}
