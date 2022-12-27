package se.sundsvall.datawarehousereader.api.model.measurement;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Measurement request parameters model")
public class MeasurementParameters extends AbstractParameterBase {

	private static final List<String> DEFAULT_SORT_BY_PROPERTY = List.of("measurementTimestamp");

	@Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1")
	@ValidUuid(nullable = true)
	private String partyId;

	@Schema(description = "Facility id", example = "735999109151401011")
	private String facilityId;

	@Schema(description = "Date and time for oldest measurement point to return. Format is yyyy-MM-dd'T'HH:mm:ss.SSSXXX, for example '2000-10-31T01:30:00.000-05:00'")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime fromDateTime;

	@Schema(description = "Date and time for youngest measurement point to return. Format is yyyy-MM-dd'T'HH:mm:ss.SSSXXX, for example '2000-10-31T01:30:00.000-05:00'")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime toDateTime;

	public MeasurementParameters() {
		this.sortBy = DEFAULT_SORT_BY_PROPERTY;
	}

	public static MeasurementParameters create() {
		return new MeasurementParameters();
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public OffsetDateTime getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(OffsetDateTime fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public OffsetDateTime getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(OffsetDateTime toDateTime) {
		this.toDateTime = toDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
			+ Objects.hash(facilityId, fromDateTime, partyId, toDateTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasurementParameters other = (MeasurementParameters) obj;
		return Objects.equals(facilityId, other.facilityId) &&
			Objects.equals(fromDateTime, other.fromDateTime) && Objects.equals(partyId, other.partyId) &&
			Objects.equals(toDateTime, other.toDateTime);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasurementParameters [partyId=").append(partyId).append(", facilityId=").append(facilityId).append(", fromDateTime=").append(fromDateTime).append(", toDateTime=").append(toDateTime).append(", page=").append(page).append(
			", limit=").append(limit).append(", sortBy=").append(sortBy).append(", sortDirection=").append(sortDirection).append("]");
		return builder.toString();
	}
}
