package se.sundsvall.datawarehousereader.api.model.agreement;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Agreement request parameters model")
public class AgreementParameters extends AbstractParameterBase {

	@ValidUuid(nullable = true)
	@Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1")
	private String partyId;

	@Schema(description = "Customer number", example = "10007")
	private String customerNumber;

	@Schema(description = "Facility Id", example = "1223334")
	private String facilityId;

	@ArraySchema(schema = @Schema(implementation = Category.class))
	private List<Category> category;

	@Schema(description = "Billing Id", example = "222333")
	private String billingId;

	@Schema(description = "Agreement Id", example = "333444")
	private String agreementId;

	@Schema(description = "Description", example = "Avtalet avser fjärrvärme")
	private String description;

	@Schema(description = "Shows if agreement is a main-agreement or not", example = "true")
	private Boolean mainAgreement;

	@Schema(description = "Shows if agreement include binding or not", example = "false")
	private Boolean binding;

	@Schema(description = "Rule of binding if exists", example = "10 mån binding")
	private String bindingRule;

	@Schema(description = "From-date in validity of agreement")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fromDate;

	@Schema(description = "To-date in validity of agreement")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate toDate;

	public static AgreementParameters create() {
		return new AgreementParameters();
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public AgreementParameters withPartyId(String partyId) {
		this.partyId = partyId;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public AgreementParameters withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public AgreementParameters withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public AgreementParameters withCategory(List<Category> category) {
		this.category = category;
		return this;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public AgreementParameters withBillingId(String billingId) {
		this.billingId = billingId;
		return this;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public AgreementParameters withAgreementId(String agreementId) {
		this.agreementId = agreementId;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AgreementParameters withDescription(String description) {
		this.description = description;
		return this;
	}

	public Boolean getMainAgreement() {
		return mainAgreement;
	}

	public void setMainAgreement(Boolean mainAgreement) {
		this.mainAgreement = mainAgreement;
	}

	public AgreementParameters withMainAgreement(Boolean mainAgreement) {
		this.mainAgreement = mainAgreement;
		return this;
	}

	public Boolean getBinding() {
		return binding;
	}

	public void setBinding(Boolean binding) {
		this.binding = binding;
	}

	public AgreementParameters withBinding(Boolean binding) {
		this.binding = binding;
		return this;
	}

	public String getBindingRule() {
		return bindingRule;
	}

	public void setBindingRule(String bindingRule) {
		this.bindingRule = bindingRule;
	}

	public AgreementParameters withBindingRule(String bindingRule) {
		this.bindingRule = bindingRule;
		return this;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public AgreementParameters withFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public AgreementParameters withToDate(LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		AgreementParameters that = (AgreementParameters) o;
		return mainAgreement == that.mainAgreement && binding == that.binding && Objects.equals(partyId, that.partyId) &&
			Objects.equals(customerNumber, that.customerNumber) && Objects.equals(facilityId, that.facilityId) &&
			category == that.category && Objects.equals(billingId, that.billingId) && Objects.equals(agreementId, that.agreementId) &&
			Objects.equals(description, that.description) && Objects.equals(bindingRule, that.bindingRule) &&
			Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), partyId, customerNumber, facilityId, category, billingId, agreementId, description,
			mainAgreement, binding, bindingRule, fromDate, toDate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgreementParameters [partyId=").append(partyId).append(", customerNumber=").append(customerNumber).append(", facilityId=").append(facilityId).append(", category=").append(category).append(", billingId=").append(billingId)
			.append(", agreementId=").append(agreementId).append(", description=").append(description).append(", mainAgreement=").append(mainAgreement).append(", binding=").append(binding).append(", bindingRule=").append(bindingRule).append(
				", fromDate=").append(fromDate).append(", toDate=").append(toDate).append(", page=").append(page).append(", limit=").append(limit).append(", sortBy=").append(sortBy).append(", sortDirection=").append(sortDirection).append("]");
		return builder.toString();
	}

}
