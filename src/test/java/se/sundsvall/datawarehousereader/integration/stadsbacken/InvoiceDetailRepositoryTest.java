package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceDetailEntity;

/**
 * invoice detail repository tests.
 * 
 * @see src/test/resources/db/scripts/testdata.sql for data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
class InvoiceDetailRepositoryTest {

	@Autowired
	private InvoiceDetailRepository repository;

	@Test
	void findAllByInvoiceNumberNoMatch() {
		assertThat(repository.findAllByInvoiceNumber(999999999)).isEmpty();
	}

	@Test
	void findAllByInvoiceNumber() {
		final var details = repository.findAllByInvoiceNumber(139345995);

		assertThat(details)
			.hasSize(3)
			.extracting(
				InvoiceDetailEntity::getAmount,
				InvoiceDetailEntity::getAmountVatExcluded,
				InvoiceDetailEntity::getInvoiceId,
				InvoiceDetailEntity::getInvoiceNumber,
				InvoiceDetailEntity::getPeriodFrom,
				InvoiceDetailEntity::getPeriodTo,
				InvoiceDetailEntity::getProductCode,
				InvoiceDetailEntity::getProductName,
				InvoiceDetailEntity::getQuantity,
				InvoiceDetailEntity::getUnit,
				InvoiceDetailEntity::getUnitPrice,
				InvoiceDetailEntity::getVat,
				InvoiceDetailEntity::getVatRate,
				InvoiceDetailEntity::getOrganizationId)
			.containsExactlyInAnyOrder(
				tuple(toBigDecimal(701.52), toBigDecimal(561.21), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1403, "Fast Elnätsavgift", 30.0, "Dagar", toBigDecimal(18.7068), toBigDecimal(140.31), 25.0, "5565027223"),
				tuple(toBigDecimal(147.20), toBigDecimal(117.76), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1404, "Elöverföring", 1070.57, "kWh", toBigDecimal(0.11), toBigDecimal(29.44), 25.0, "5565027223"),
				tuple(toBigDecimal(464.36), toBigDecimal(371.49), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1413, "Energiskatt", 1070.57, "kWh", toBigDecimal(0.347), toBigDecimal(92.87), 25.0, "5565027223"));
	}

	@Test
	void getInvoiceDetailsNoMatch() {
		assertThat(repository.findAllByOrganizationIdAndInvoiceNumber("5565027223", 999999999)).isEmpty();
	}

	@Test
	void getInvoiceDetails() {
		final var details = repository.findAllByOrganizationIdAndInvoiceNumber("5565027223", 139345995);

		assertThat(details)
			.hasSize(3)
			.extracting(
				InvoiceDetailEntity::getAmount,
				InvoiceDetailEntity::getAmountVatExcluded,
				InvoiceDetailEntity::getInvoiceId,
				InvoiceDetailEntity::getInvoiceNumber,
				InvoiceDetailEntity::getPeriodFrom,
				InvoiceDetailEntity::getPeriodTo,
				InvoiceDetailEntity::getProductCode,
				InvoiceDetailEntity::getProductName,
				InvoiceDetailEntity::getQuantity,
				InvoiceDetailEntity::getUnit,
				InvoiceDetailEntity::getUnitPrice,
				InvoiceDetailEntity::getVat,
				InvoiceDetailEntity::getVatRate,
				InvoiceDetailEntity::getOrganizationId)
			.containsExactlyInAnyOrder(
				tuple(toBigDecimal(701.52), toBigDecimal(561.21), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1403, "Fast Elnätsavgift", 30.0, "Dagar", toBigDecimal(18.7068), toBigDecimal(140.31), 25.0, "5565027223"),
				tuple(toBigDecimal(147.20), toBigDecimal(117.76), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1404, "Elöverföring", 1070.57, "kWh", toBigDecimal(0.11), toBigDecimal(29.44), 25.0, "5565027223"),
				tuple(toBigDecimal(464.36), toBigDecimal(371.49), 1393459, 139345995L, "2019-09-01", "2019-09-30", 1413, "Energiskatt", 1070.57, "kWh", toBigDecimal(0.347), toBigDecimal(92.87), 25.0, "5565027223"));
	}

	private static BigDecimal toBigDecimal(double number) {
		return BigDecimal.valueOf(number).setScale(4);
	}
}
