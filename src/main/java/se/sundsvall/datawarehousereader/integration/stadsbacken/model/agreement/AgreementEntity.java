package se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(schema = "kundinfo", name = "vAgreements")
@IdClass(AgreementKey.class)
public class AgreementEntity {

	@Id
	@Column(name = "agreementId", nullable = false, insertable = false, updatable = false)
	private Integer agreementId;

	@Id
	@Column(name = "billingId", nullable = false, insertable = false, updatable = false)
	private Integer billingId;

	@Column(name = "uuid", insertable = false, updatable = false, columnDefinition = "uniqueidentifier")
	private String uuid;

	@Column(name = "customerorgid", insertable = false, updatable = false, columnDefinition = "varchar(8000)")
	private String customerOrgId;

	@Column(name = "customerid", nullable = false, insertable = false, updatable = false)
	private Integer customerId;

	@Column(name = "facilityId", insertable = false, updatable = false, columnDefinition = "varchar(255)")
	private String facilityId;

	@Column(name = "category", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String category;

	@Column(name = "description", insertable = false, updatable = false, columnDefinition = "varchar(4096)")
	private String description;

	@Column(name = "mainAgreement", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(5)")
	private String mainAgreement;

	@Column(name = "binding", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(5)")
	private String binding;

	@Column(name = "bindingRule", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String bindingRule;

	@Column(name = "fromDate", insertable = false, updatable = false, columnDefinition = "datetime")
	private LocalDateTime fromDate;

	@Column(name = "toDate", insertable = false, updatable = false, columnDefinition = "datetime")
	private LocalDateTime toDate;

	public static AgreementEntity create() {
		return new AgreementEntity();
	}

	public Integer getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Integer agreementId) {
		this.agreementId = agreementId;
	}

	public AgreementEntity withAgreementId(Integer agreementId) {
		this.setAgreementId(agreementId);
		return this;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AgreementEntity withUuid(String uuid) {
		this.setUuid(uuid);
		return this;
	}

	public String getCustomerOrgId() {
		return customerOrgId;
	}

	public void setCustomerOrgId(String customerOrgId) {
		this.customerOrgId = customerOrgId;
	}

	public AgreementEntity withCustomerOrgId(String customerOrgId) {
		this.setCustomerOrgId(customerOrgId);
		return this;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public AgreementEntity withCustomerId(Integer customerId) {
		this.setCustomerId(customerId);
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public AgreementEntity withFacilityId(String facilityId) {
		this.setFacilityId(facilityId);
		return this;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public AgreementEntity withCategory(String category) {
		this.setCategory(category);
		return this;
	}

	public Integer getBillingId() {
		return billingId;
	}

	public void setBillingId(Integer billingId) {
		this.billingId = billingId;
	}

	public AgreementEntity withBillingId(Integer billingId) {
		this.setBillingId(billingId);
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AgreementEntity withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public String getMainAgreement() {
		return mainAgreement;
	}

	public void setMainAgreement(String mainAgreement) {
		this.mainAgreement = mainAgreement;
	}

	public AgreementEntity withMainAgreement(String mainAgreement) {
		this.setMainAgreement(mainAgreement);
		return this;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public AgreementEntity withBinding(String binding) {
		this.setBinding(binding);
		return this;
	}

	public String getBindingRule() {
		return bindingRule;
	}

	public void setBindingRule(String bindingRule) {
		this.bindingRule = bindingRule;
	}

	public AgreementEntity withBindingRule(String bindingRule) {
		this.setBindingRule(bindingRule);
		return this;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public AgreementEntity withFromDate(LocalDateTime fromDate) {
		this.setFromDate(fromDate);
		return this;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}

	public AgreementEntity withToDate(LocalDateTime toDate) {
		this.setToDate(toDate);
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agreementId, uuid, customerOrgId, customerId, facilityId, category, billingId, description, mainAgreement, binding, bindingRule, fromDate, toDate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AgreementEntity that = (AgreementEntity) o;
		return Objects.equals(agreementId, that.agreementId) && Objects.equals(uuid, that.uuid) && Objects.equals(customerOrgId, that.customerOrgId) && Objects.equals(customerId, that.customerId) && Objects.equals(facilityId, that.facilityId)
			&& Objects.equals(category, that.category) && Objects.equals(billingId, that.billingId) && Objects.equals(description, that.description) && Objects.equals(mainAgreement, that.mainAgreement) && Objects.equals(binding, that.binding)
			&& Objects.equals(bindingRule, that.bindingRule) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("AgreementEntity [agreementId=").append(agreementId)
			.append(", uuid='").append(uuid).append(", customerOrgId='").append(customerOrgId)
			.append(", customerId='").append(customerId).append(", facilityId='").append(facilityId)
			.append(", category='").append(category).append(", billingId=").append(billingId)
			.append(", description='").append(description).append(", mainAgreement='").append(mainAgreement).append(", binding='").append(binding)
			.append(", bindingRule='").append(bindingRule).append(", fromDate=").append(fromDate)
			.append(", toDate=").append(toDate).append("]").toString();
	}
}
