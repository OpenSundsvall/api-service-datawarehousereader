package se.sundsvall.datawarehousereader.api.model.installedbase;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Installed base item model")
public class InstalledBaseItem {
	
	@Schema(description = "Company", example = "Sundsvall Energi AB", accessMode = READ_ONLY)
	private String company;
	
	@Schema(description = "Customer number", example = "104327", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(description = "Type", example = "Fjärrvärme", accessMode = READ_ONLY)
	private String type;

	@Schema(description = "Facility id", example = "735999109270751042", accessMode = READ_ONLY)
	private String facilityId;

	@Schema(description = "Placement id", example = "5263", accessMode = READ_ONLY)
	private int placementId;

	@Schema(description = "Care of address", example = "Agatha Malm", accessMode = READ_ONLY)
	private String careOf;

	@Schema(description = "Street", example = "Storgatan 9", accessMode = READ_ONLY)
	private String street;

	@Schema(description = "Postal code", example = "85230", accessMode = READ_ONLY)
	private String postCode;

	@Schema(description = "City", example = "Sundsvall", accessMode = READ_ONLY)
	private String city;

	@Schema(description = "Property designation", example = "Södermalm 1:27", accessMode = READ_ONLY)
	private String propertyDesignation;

	@Schema(description = "From date", example = "2019-01-01", accessMode = READ_ONLY)
	private LocalDate dateFrom;

	@Schema(description = "To date", example = "2022-12-31", accessMode = READ_ONLY)
	private LocalDate dateTo;
	
	@ArraySchema(schema = @Schema(implementation = InstalledBaseItemMetaData.class, accessMode = READ_ONLY), maxItems = 1000)
	private List<InstalledBaseItemMetaData> metaData;
	
	public static InstalledBaseItem create() {
		return new InstalledBaseItem();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public InstalledBaseItem withCompany(String company) {
		this.company = company;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public InstalledBaseItem withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InstalledBaseItem withType(String type) {
		this.type = type;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public InstalledBaseItem withCareOf(String careOf) {
		this.careOf = careOf;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public InstalledBaseItem withStreet(String street) {
		this.street = street;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public InstalledBaseItem withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public int getPlacementId() {
		return placementId;
	}

	public void setPlacementId(int placementId) {
		this.placementId = placementId;
	}

	public InstalledBaseItem withPlacementId(int placementId) {
		this.placementId = placementId;
		return this;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public InstalledBaseItem withPostCode(String postCode) {
		this.postCode = postCode;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public InstalledBaseItem withCity(String city) {
		this.city = city;
		return this;
	}

	public String getPropertyDesignation() {
		return propertyDesignation;
	}

	public void setPropertyDesignation(String propertyDesignation) {
		this.propertyDesignation = propertyDesignation;
	}

	public InstalledBaseItem withPropertyDesignation(String propertyDesignation) {
		this.propertyDesignation = propertyDesignation;
		return this;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public InstalledBaseItem withDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
		return this;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	
	public InstalledBaseItem withDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
		return this;
	}
	
	public List<InstalledBaseItemMetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<InstalledBaseItemMetaData> metaData) {
		this.metaData = metaData;
	}

	public InstalledBaseItem withMetaData(List<InstalledBaseItemMetaData> metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(careOf, city, company, customerNumber, dateFrom, dateTo, facilityId, metaData, placementId, postCode, propertyDesignation, street, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstalledBaseItem other = (InstalledBaseItem) obj;
		return Objects.equals(careOf, other.careOf) && Objects.equals(city, other.city) && Objects.equals(company, other.company) && Objects.equals(customerNumber, other.customerNumber) && Objects.equals(dateFrom, other.dateFrom) && Objects.equals(
			dateTo, other.dateTo) && Objects.equals(facilityId, other.facilityId) && Objects.equals(metaData, other.metaData) && placementId == other.placementId && Objects.equals(postCode, other.postCode) && Objects.equals(propertyDesignation,
				other.propertyDesignation) && Objects.equals(street, other.street) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstalledBaseItem [company=").append(company).append(", customerNumber=").append(customerNumber).append(", type=").append(type).append(", facilityId=").append(facilityId).append(", placementId=").append(placementId).append(
			", careOf=").append(careOf).append(", street=").append(street).append(", postCode=").append(postCode).append(", city=").append(city).append(", propertyDesignation=").append(propertyDesignation).append(", dateFrom=").append(dateFrom)
			.append(", dateTo=").append(dateTo).append(", metaData=").append(metaData).append("]");
		return builder.toString();
	}

}
