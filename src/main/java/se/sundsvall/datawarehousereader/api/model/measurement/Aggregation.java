package se.sundsvall.datawarehousereader.api.model.measurement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data point aggregation granularity", enumAsRef = true)
public enum Aggregation {
	HOUR,
	DAY,
	MONTH,
	YEAR
}
