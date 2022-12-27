package se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer;

import java.io.Serializable;
import java.util.Objects;

public class CustomerKey implements Serializable {
	private static final long serialVersionUID = 8683879823650117181L;

	private Integer customerId;

	private String organizationId;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, organizationId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerKey other = (CustomerKey) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(organizationId, other.organizationId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerKey [customerId=").append(customerId).append(", organizationId=").append(organizationId)
				.append("]");
		return builder.toString();
	}
}
