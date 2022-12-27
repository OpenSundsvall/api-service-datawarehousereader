package se.sundsvall.datawarehousereader.api.model.installedbase;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;

@Schema(description = "Installed base request parameters model")
public class InstalledBaseParameters extends AbstractParameterBase {

	@Schema(description = "Company", example = "Sundsvall Energi AB")
	private String company;

	@Schema(description = "Customer number", example = "104327")
	private String customerNumber;

	@Schema(description = "Type", example = "Fjärrvärme")
	private String type;

	@Schema(description = "Facility id", example = "735999109270751042")
	private String facilityId;

	@Schema(description = "Care of address", example = "Agatha Malm")
	private String careOf;

	@Schema(description = "Street", example = "Storgatan 9")
	private String street;

	@Schema(description = "Postal code", example = "85230")
	private String postCode;

	@Schema(description = "City", example = "Sundsvall")
	private String city;

	@Schema(description = "Property designation", example = "Södermalm 1:27")
	private String propertyDesignation;

	public static InstalledBaseParameters create() {
		return new InstalledBaseParameters();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPropertyDesignation() {
		return propertyDesignation;
	}

	public void setPropertyDesignation(String propertyDesignation) {
		this.propertyDesignation = propertyDesignation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(careOf, city, company, customerNumber, facilityId, postCode, propertyDesignation, street, type);
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
		InstalledBaseParameters other = (InstalledBaseParameters) obj;
		return Objects.equals(careOf, other.careOf) && Objects.equals(city, other.city) && Objects.equals(company, other.company) && Objects.equals(customerNumber, other.customerNumber) && Objects.equals(facilityId, other.facilityId) && Objects
			.equals(postCode, other.postCode) && Objects.equals(propertyDesignation, other.propertyDesignation) && Objects.equals(street, other.street) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstalledBaseParameters [company=").append(company).append(", customerNumber=").append(customerNumber).append(", type=").append(type).append(", facilityId=").append(facilityId).append(", careOf=").append(careOf).append(
			", street=").append(street).append(", postCode=").append(postCode).append(", city=").append(city).append(", propertyDesignation=").append(propertyDesignation).append(", page=").append(page).append(", limit=").append(limit).append(
				", sortBy=").append(sortBy).append(", sortDirection=").append(sortDirection).append("]");
		return builder.toString();
	}
}
