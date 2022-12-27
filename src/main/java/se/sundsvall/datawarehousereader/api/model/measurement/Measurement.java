package se.sundsvall.datawarehousereader.api.model.measurement;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.Category;

@Schema(description = "Measurement model")
public class Measurement {

	@Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1", accessMode = READ_ONLY)
	private String partyId;

	@Schema(description = "Facility id", example = "735999109151401011", accessMode = READ_ONLY)
	private String facilityId;

	@Schema(implementation = Category.class, accessMode = READ_ONLY)
	private Category category;

	@Schema(implementation = Aggregation.class, accessMode = READ_ONLY)
	private Aggregation aggregatedOn;

	@Schema(description = "Measurement type", example = "Energy", accessMode = READ_ONLY)
	private String measurementType;

	@Schema(description = "Measurement unit", example = "kWh", accessMode = READ_ONLY)
	private String unit;

	@Schema(description = "Measurement value", example = "1292.7500000000", accessMode = READ_ONLY)
	private BigDecimal value;

	@Schema(description = "Date and time for measurement point. Format is yyyy-MM-dd'T'HH:mm:ss.SSSXXX, for example '2000-10-31T01:30:00.000-05:00'", accessMode = READ_ONLY)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime timestamp;

	@ArraySchema(schema = @Schema(implementation = MeasurementMetaData.class, accessMode = READ_ONLY))
	private List<MeasurementMetaData> metaData;
	
	@Schema(description = "Interpolation information. Zero means that no interpolations has been done. Value greater than zero tells how many values in the serie that has been interpolated/calculated.", example = "13", accessMode = READ_ONLY)
	private int interpolation;

	public static Measurement create() {
		return new Measurement();
	}
	
	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public Measurement withPartyId(String partyId) {
		this.partyId = partyId;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Measurement withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Measurement withCategory(Category category) {
		this.category = category;
		return this;
	}

	public Aggregation getAggregatedOn() {
		return aggregatedOn;
	}

	public void setAggregatedOn(Aggregation aggregation) {
		this.aggregatedOn = aggregation;
	}

	public Measurement withAggregatedOn(Aggregation aggregation) {
		this.aggregatedOn = aggregation;
		return this;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	public Measurement withMeasurementType(String measurementType) {
		this.measurementType = measurementType;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Measurement withUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Measurement withValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Measurement withTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public List<MeasurementMetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<MeasurementMetaData> metaData) {
		this.metaData = metaData;
	}

	public Measurement withMetaData(List<MeasurementMetaData> metaData) {
		this.metaData = metaData;
		return this;
	}

	public int getInterpolation() {
		return interpolation;
	}

	public void setInterpolation(int interpolation) {
		this.interpolation = interpolation;
	}
	
	public Measurement withInterpolation(int interpolation) {
		this.interpolation = interpolation;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, facilityId, interpolation, measurementType, metaData, partyId, aggregatedOn,
				timestamp, unit, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measurement other = (Measurement) obj;
		return category == other.category && Objects.equals(facilityId, other.facilityId)
				&& interpolation == other.interpolation && Objects.equals(measurementType, other.measurementType)
				&& Objects.equals(metaData, other.metaData) && Objects.equals(partyId, other.partyId)
				&& aggregatedOn == other.aggregatedOn && Objects.equals(timestamp, other.timestamp)
				&& Objects.equals(unit, other.unit) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Measurement [partyId=").append(partyId).append(", facilityId=").append(facilityId)
				.append(", category=").append(category).append(", aggregatedOn=").append(aggregatedOn)
				.append(", measurementType=").append(measurementType).append(", unit=").append(unit).append(", value=")
				.append(value).append(", timestamp=").append(timestamp).append(", metaData=").append(metaData)
				.append(", interpolation=").append(interpolation).append("]");
		return builder.toString();
	}
}
