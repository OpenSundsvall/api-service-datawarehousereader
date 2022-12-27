package se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement;

import java.io.Serializable;
import java.util.Objects;

public class MeasurementDistrictHeatingKey implements Serializable {
	private static final long serialVersionUID = -1348815619727439020L;

	private String customerOrgId;

	private String facilityId;
	
	private Integer readingSequence;

	public String getCustomerOrgId() {
		return customerOrgId;
	}

	public void setCustomerOrgId(String customerOrgId) {
		this.customerOrgId = customerOrgId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Integer getReadingSequence() {
		return readingSequence;
	}

	public void setReadingSequence(Integer readingSequence) {
		this.readingSequence = readingSequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerOrgId, facilityId, readingSequence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasurementDistrictHeatingKey other = (MeasurementDistrictHeatingKey) obj;
		return Objects.equals(customerOrgId, other.customerOrgId) && Objects.equals(facilityId, other.facilityId)
				&& Objects.equals(readingSequence, other.readingSequence);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasurementDistrictHeatingKey [customerOrgId=").append(customerOrgId)
				.append(", facilityId=").append(facilityId).append(", readingSequence=").append(readingSequence)
				.append("]");
		return builder.toString();
	}
}
