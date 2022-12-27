package se.sundsvall.datawarehousereader.api.model.measurement;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.DAY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.HOUR;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.YEAR;

import org.junit.jupiter.api.Test;

class AggregationTest {

	@Test
	void enums() {
		assertThat(Aggregation.values()).containsExactlyInAnyOrder(HOUR, DAY, MONTH, YEAR);
	}

	@Test
	void enumValues() {
		assertThat(HOUR).hasToString("HOUR");
		assertThat(DAY).hasToString("DAY");
		assertThat(MONTH).hasToString("MONTH");
		assertThat(YEAR).hasToString("YEAR");
	}
}
