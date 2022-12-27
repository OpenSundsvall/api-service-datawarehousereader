package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;

/**
 * Customer repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;

	@Test
	void getCustomerNoMatch() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create().withCustomerNumber("99999"), null, PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isZero();
		assertThat(page.getTotalPages()).isZero();
		assertThat(page.getTotalElements()).isZero();
		assertThat(page.getContent()).isEmpty();
	}

	@Test
	void getCustomerNoFilters() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create(), null, PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(6);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getContent())
			.hasSize(6)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactlyInAnyOrder(
				tuple(691071, "197706010123", "Private", "5564786647", "Sundsvall Energi AB"),
				tuple(600606, "5512345678", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(38308, "5523456789", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(600675, "2020001000", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5565027223", "Sundsvall Elnät"));
	}

	@Test
	void getCustomerByCustomerId() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create().withCustomerNumber("10335"), null, PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
			.hasSize(2)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactlyInAnyOrder(
				tuple(10335, "5534567890", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5565027223", "Sundsvall Elnät"));
	}

	@Test
	void getCustomerByCustomerIdPageOneOfTwo() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create().withCustomerNumber("10335"), null, PageRequest.of(0, 1));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactly(
				tuple(10335, "5534567890", "Enterprise", "5564786647", "Sundsvall Energi AB"));
	}

	@Test
	void getCustomerBySingleCustomerOrgId() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create(), List.of("197706010123"), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactly(
				tuple(691071, "197706010123", "Private", "5564786647", "Sundsvall Energi AB"));
	}

	@Test
	void getCustomersByMultipleCustomerOrgIds() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create(), List.of("197706010123", "5512345678"), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(2);
		assertThat(page.getContent())
			.hasSize(2)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactlyInAnyOrder(
				tuple(691071, "197706010123", "Private", "5564786647", "Sundsvall Energi AB"),
				tuple(600606, "5512345678", "Enterprise", "5564786647", "Sundsvall Energi AB"));
	}

	@Test
	void getCustomersByEmptyCustomerOrgIdList() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create(), emptyList(), PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(6);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getContent())
			.hasSize(6)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactlyInAnyOrder(
				tuple(691071, "197706010123", "Private", "5564786647", "Sundsvall Energi AB"),
				tuple(600606, "5512345678", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(38308, "5523456789", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(600675, "2020001000", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5565027223", "Sundsvall Elnät"));
	}

	@Test
	void getCustomerByOrganizationNumber() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create().withOrganizationNumber("5564786647"), null, PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(5);
		assertThat(page.getContent())
			.hasSize(5)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactlyInAnyOrder(
				tuple(691071, "197706010123", "Private", "5564786647", "Sundsvall Energi AB"),
				tuple(600606, "5512345678", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(38308, "5523456789", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(600675, "2020001000", "Enterprise", "5564786647", "Sundsvall Energi AB"),
				tuple(10335, "5534567890", "Enterprise", "5564786647", "Sundsvall Energi AB"));
	}

	@Test
	void getCustomerByOrganizationName() {
		final var page = repository.findAllByParameters(CustomerEngagementParameters.create().withOrganizationName("Sundsvall Elnät"), null, PageRequest.of(0, 100));

		assertThat(page.getNumber()).isZero();
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getTotalPages()).isEqualTo(1);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getContent())
			.hasSize(1)
			.extracting(CustomerEntity::getCustomerId, CustomerEntity::getCustomerOrgId, CustomerEntity::getCustomerType, CustomerEntity::getOrganizationId, CustomerEntity::getOrganizationName)
			.containsExactly(
				tuple(10335, "5534567890", "Enterprise", "5565027223", "Sundsvall Elnät"));
	}
}
