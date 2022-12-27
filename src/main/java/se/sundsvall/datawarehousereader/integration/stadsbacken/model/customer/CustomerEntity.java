package se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(schema = "kundinfo", name = "vCustomer")
@IdClass(CustomerKey.class)
public class CustomerEntity {

	@Id
	@Column(name = "customerid", nullable = false, insertable = false, updatable = false)
	private Integer customerId;

	@Id
	@Column(name = "organizationid", insertable = false, updatable = false, columnDefinition = "varchar(10)")
	private String organizationId;
	
	@Column(name = "customerorgid", insertable = false, updatable = false)
	private String customerOrgId;
	
	@Column(name = "customertype", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(10)")
	private String customerType;
	
	@Column(name = "organizationname", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String organizationName;

	public static CustomerEntity create() {
		return new CustomerEntity();
	}
	
	public String getCustomerOrgId() {
		return customerOrgId;
	}

	public void setCustomerOrgId(String customerOrgId) {
		this.customerOrgId = customerOrgId;
	}

	public CustomerEntity withCustomerOrgId(String customerOrgId) {
		this.customerOrgId = customerOrgId;
		return this;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public CustomerEntity withCustomerType(String customerType) {
		this.customerType = customerType;
		return this;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerEntity withCustomerId(Integer customerId) {
		this.customerId = customerId;
		return this;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public CustomerEntity withOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public CustomerEntity withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, customerOrgId, customerType, organizationId, organizationName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerEntity other = (CustomerEntity) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(customerOrgId, other.customerOrgId)
				&& Objects.equals(customerType, other.customerType)
				&& Objects.equals(organizationId, other.organizationId)
				&& Objects.equals(organizationName, other.organizationName);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerEntity [customerId=").append(customerId).append(", customerOrgId=")
				.append(customerOrgId).append(", customerType=").append(customerType).append(", organizationId=")
				.append(organizationId).append(", organizationName=").append(organizationName).append("]");
		return builder.toString();
	}
}
