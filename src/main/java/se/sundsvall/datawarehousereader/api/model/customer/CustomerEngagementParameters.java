package se.sundsvall.datawarehousereader.api.model.customer;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Customer engagement request parameters model")
public class CustomerEngagementParameters extends AbstractParameterBase {

	private static final List<String> DEFAULT_SORT_BY_PROPERTY = List.of("customerOrgId");

	@ArraySchema(schema = @Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1"))
	private List<@ValidUuid String> partyId;

	@Schema(description = "Customer number", example = "10007")
	private String customerNumber;

	@Schema(description = "Organization number for counterpart of engagement", example = "5565027223")
	private String organizationNumber;

	@Schema(description = "Organization name for counterpart of engagement", example = "Sundsvall Elnät")
	private String organizationName;

	public CustomerEngagementParameters() {
		this.sortBy = DEFAULT_SORT_BY_PROPERTY;
	}

	public static CustomerEngagementParameters create() {
		return new CustomerEngagementParameters();
	}

	public List<String> getPartyId() {
		return partyId;
	}

	public void setPartyId(List<String> partyId) {
		this.partyId = partyId;
	}

	public CustomerEngagementParameters withPartyId(List<String> partyId) {
		this.partyId = partyId;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerEngagementParameters withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public CustomerEngagementParameters withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public CustomerEngagementParameters withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(customerNumber, organizationName, organizationNumber, partyId);
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
		CustomerEngagementParameters other = (CustomerEngagementParameters) obj;
		return Objects.equals(customerNumber, other.customerNumber) && Objects.equals(organizationName, other.organizationName) && Objects.equals(organizationNumber, other.organizationNumber) && Objects.equals(partyId, other.partyId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerEngagementParameters [partyId=").append(partyId).append(", customerNumber=").append(customerNumber).append(", organizationNumber=").append(organizationNumber).append(", organizationName=").append(organizationName)
			.append(", page=").append(page).append(", limit=").append(limit).append(", sortBy=").append(sortBy).append(", sortDirection=").append(sortDirection).append("]");
		return builder.toString();
	}
}
