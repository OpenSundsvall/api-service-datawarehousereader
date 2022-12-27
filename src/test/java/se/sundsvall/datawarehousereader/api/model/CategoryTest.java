package se.sundsvall.datawarehousereader.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static se.sundsvall.datawarehousereader.api.model.Category.COMMUNICATION;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_COOLING;
import static se.sundsvall.datawarehousereader.api.model.Category.DISTRICT_HEATING;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY_TRADE;
import static se.sundsvall.datawarehousereader.api.model.Category.WASTE_MANAGEMENT;
import static se.sundsvall.datawarehousereader.api.model.Category.WATER;

import org.junit.jupiter.api.Test;

class CategoryTest {

	@Test
	void enums() {
		assertThat(Category.values())
			.containsExactlyInAnyOrder(COMMUNICATION, DISTRICT_COOLING, DISTRICT_HEATING, ELECTRICITY, ELECTRICITY_TRADE, WASTE_MANAGEMENT, WATER);
	}

	@Test
	void stadsbackenValues() {
		assertThat(COMMUNICATION.toStadsbackenValue()).isEqualTo("Bredband");
		assertThat(DISTRICT_COOLING.toStadsbackenValue()).isEqualTo("Fjärrkyla");
		assertThat(DISTRICT_HEATING.toStadsbackenValue()).isEqualTo("Fjärrvärme");
		assertThat(ELECTRICITY.toStadsbackenValue()).isEqualTo("El");
		assertThat(ELECTRICITY_TRADE.toStadsbackenValue()).isEqualTo("Elhandel");
		assertThat(WASTE_MANAGEMENT.toStadsbackenValue()).isEqualTo("Avfallsvåg");
		assertThat(WATER.toStadsbackenValue()).isEqualTo("Vatten");
	}

	@Test
	void forStadsbackenValues() {
		assertThat(Category.forStadsbackenValue("El")).isEqualTo(ELECTRICITY);
		assertThat(Category.forStadsbackenValue("Elhandel")).isEqualTo(ELECTRICITY_TRADE);
		assertThat(Category.forStadsbackenValue("Fjärrvärme")).isEqualTo(DISTRICT_HEATING);
		assertThat(Category.forStadsbackenValue("Fjärrkyla")).isEqualTo(DISTRICT_COOLING);
		assertThat(Category.forStadsbackenValue("Avfallsvåg")).isEqualTo(WASTE_MANAGEMENT);
		assertThat(Category.forStadsbackenValue("Vatten")).isEqualTo(WATER);
		assertThat(Category.forStadsbackenValue("Bredband")).isEqualTo(COMMUNICATION);
	}

	@Test
	void unkownStadsbacken() {
		final var exception = assertThrows(IllegalArgumentException.class, () -> Category.forStadsbackenValue("UnknownValue"));

		assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("Illegal enum value: 'UnknownValue'");
	}
}
