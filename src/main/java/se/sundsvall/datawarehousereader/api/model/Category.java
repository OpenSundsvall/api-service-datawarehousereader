package se.sundsvall.datawarehousereader.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

import static java.lang.String.format;
import static java.util.Arrays.stream;

@Schema(description = "Category", enumAsRef = true)
public enum Category {
	COMMUNICATION("Bredband"),
	DISTRICT_COOLING("Fjärrkyla"),
	DISTRICT_HEATING("Fjärrvärme"),
	ELECTRICITY("El"),
	ELECTRICITY_TRADE("Elhandel"),
	WASTE_MANAGEMENT("Avfallsvåg"),
	WATER("Vatten");

	private final String stadsbackenValue;

	Category(String stadsbackenValue) {
		this.stadsbackenValue = stadsbackenValue;
	}

	public String toStadsbackenValue() {
		return this.stadsbackenValue;
	}

	public static Category forStadsbackenValue(String stadsbackenValue) {
		return stream(values())
				.filter(enumObj -> enumObj.stadsbackenValue.equalsIgnoreCase(stadsbackenValue))
				.findFirst().orElseThrow(() -> new IllegalArgumentException(format("Illegal enum value: '%s'", stadsbackenValue)));
	}
}
