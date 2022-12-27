package se.sundsvall.datawarehousereader.api.model.customer;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.CustomerType;

@Schema(description = "Customer engagement model")
public class CustomerEngagement {

	@Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1", accessMode = READ_ONLY)
	private String partyId;

	@Schema(description = "Customer organization number, for internal use only", example = "5534567890", accessMode = READ_ONLY, hidden = true)
	private String customerOrgNumber;

	@Schema(implementation = CustomerType.class, accessMode = READ_ONLY)
	private CustomerType customerType;

	@Schema(description = "Customer number", example = "10007", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(description = "Organization number for counterpart of engagement", example = "5565027223", accessMode = READ_ONLY)
	private String organizationNumber;

	@Schema(description = "Organization name for counterpart of engagement", example = "Sundsvall Elnät", accessMode = READ_ONLY)
	private String organizationName;

	public static CustomerEngagement create() {
		return new CustomerEngagement();
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public CustomerEngagement withPartyId(String partyId) {
		this.partyId = partyId;
		return this;
	}

	public String getCustomerOrgNumber() {
		return customerOrgNumber;
	}

	public void setCustomerOrgNumber(String customerOrgNumber) {
		this.customerOrgNumber = customerOrgNumber;
	}

	public CustomerEngagement withCustomerOrgNumber(String customerOrgNumber) {
		this.customerOrgNumber = customerOrgNumber;
		return this;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public CustomerEngagement withCustomerType(CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerEngagement withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public CustomerEngagement withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public CustomerEngagement withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerNumber, customerOrgNumber, customerType, organizationName, organizationNumber,
			partyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerEngagement other = (CustomerEngagement) obj;
		return Objects.equals(customerNumber, other.customerNumber)
			&& Objects.equals(customerOrgNumber, other.customerOrgNumber) && customerType == other.customerType
			&& Objects.equals(organizationName, other.organizationName)
			&& Objects.equals(organizationNumber, other.organizationNumber)
			&& Objects.equals(partyId, other.partyId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerEngagement [partyId=").append(partyId).append(", customerOrgNumber=")
			.append(customerOrgNumber).append(", customerType=").append(customerType).append(", customerNumber=")
			.append(customerNumber).append(", organizationNumber=").append(organizationNumber)
			.append(", organizationName=").append(organizationName).append("]");
		return builder.toString();
	}
}
