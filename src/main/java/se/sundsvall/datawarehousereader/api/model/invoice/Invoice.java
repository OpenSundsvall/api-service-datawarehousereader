package se.sundsvall.datawarehousereader.api.model.invoice;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.CustomerType;

@Schema(description = "Invoice model")
public class Invoice {

	@Schema(description = "Customer number", example = "39195", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(implementation = CustomerType.class, accessMode = READ_ONLY)
	private CustomerType customerType;

	@Schema(description = "Facility id", example = "735999109151401011", accessMode = READ_ONLY)
	private String facilityId;

	@Schema(description = "Invoice number", example = "767915994", accessMode = READ_ONLY)
	private Long invoiceNumber;

	@Schema(description = "Invoice date", example = "2022-01-15", accessMode = READ_ONLY)
	private LocalDate invoiceDate;

	@Schema(description = "invoice name", example = "765801493.pdf", accessMode = READ_ONLY)
	private String invoiceName;

	@Schema(description = "Invoice type", example = "Faktura", accessMode = READ_ONLY)
	private String invoiceType;

	@Schema(description = "Invoice description", example = "Fjärrvärme", accessMode = READ_ONLY)
	private String invoiceDescription;

	@Schema(description = "Invoice status", example = "Skickad", accessMode = READ_ONLY)
	private String invoiceStatus;

	@Schema(description = "Ocr number", example = "767915994", accessMode = READ_ONLY)
	private Long ocrNumber;

	@Schema(description = "Due date", example = "2022-01-31", accessMode = READ_ONLY)
	private LocalDate dueDate;

	@Schema(description = "Total amount", example = "1116.00", accessMode = READ_ONLY)
	private BigDecimal totalAmount;

	@Schema(description = "Amount included VAT", example = "1115.62", accessMode = READ_ONLY)
	private BigDecimal amountVatIncluded;

	@Schema(description = "Amount excluded VAT", example = "892.50", accessMode = READ_ONLY)
	private BigDecimal amountVatExcluded;

	@Schema(description = "Amount eligible for VAT", example = "892.50", accessMode = READ_ONLY)
	private BigDecimal vatEligibleAmount;

	@Schema(description = "Rounding", example = "0.38", accessMode = READ_ONLY)
	private BigDecimal rounding;

	@Schema(description = "VAT", example = "892.50", accessMode = READ_ONLY)
	private BigDecimal vat;

	@Schema(description = "Reversed VAT", example = "false", accessMode = READ_ONLY)
	private Boolean reversedVat;

	@Schema(description = "Currency", example = "sek", accessMode = READ_ONLY)
	private String currency;

	@Schema(description = "Organization group", example = "stadsbacken", accessMode = READ_ONLY)
	private String organizationGroup;

	@Schema(description = "Organization number of invoice issuer", example = "5565027223", accessMode = READ_ONLY)
	private String organizationNumber;

	@Schema(description = "Adminstration", example = "Sundsvall Elnät", accessMode = READ_ONLY)
	private String administration;

	@Schema(description = "Street", example = "Storgatan 44", accessMode = READ_ONLY)
	private String street;

	@Schema(description = "Postal code", example = "85230", accessMode = READ_ONLY)
	private String postCode;

	@Schema(description = "City", example = "Sundsvall", accessMode = READ_ONLY)
	private String city;

	@Schema(description = "Care of address", example = "Agatha Malm", accessMode = READ_ONLY)
	private String careOf;

	@Schema(description = "Is pdf-version of invoice available", example = "false", accessMode = READ_ONLY)
	private Boolean pdfAvailable;

	public static Invoice create() {
		return new Invoice();
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Invoice withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Invoice withCustomerType(CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Invoice withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Invoice withInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Invoice withInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Invoice withInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Invoice withInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	public Invoice withInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
		return this;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Invoice withInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public Long getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(Long ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public Invoice withOcrNumber(Long ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Invoice withDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Invoice withTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public BigDecimal getAmountVatIncluded() {
		return amountVatIncluded;
	}

	public void setAmountVatIncluded(BigDecimal amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
	}

	public Invoice withAmountVatIncluded(BigDecimal amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
		return this;
	}

	public BigDecimal getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public Invoice withAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public BigDecimal getVatEligibleAmount() {
		return vatEligibleAmount;
	}

	public void setVatEligibleAmount(BigDecimal vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
	}

	public Invoice withVatEligibleAmount(BigDecimal vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
		return this;
	}

	public BigDecimal getRounding() {
		return rounding;
	}

	public void setRounding(BigDecimal rounding) {
		this.rounding = rounding;
	}

	public Invoice withRounding(BigDecimal rounding) {
		this.rounding = rounding;
		return this;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public Invoice withVat(BigDecimal vat) {
		this.vat = vat;
		return this;
	}

	public Boolean getReversedVat() {
		return reversedVat;
	}

	public void setReversedVat(Boolean reversedVat) {
		this.reversedVat = reversedVat;
	}

	public Invoice withReversedVat(Boolean reversedVat) {
		this.reversedVat = reversedVat;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Invoice withCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public Invoice withOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public Invoice withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public Invoice withAdministration(String administration) {
		this.administration = administration;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Invoice withStreet(String street) {
		this.street = street;
		return this;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Invoice withPostCode(String postCode) {
		this.postCode = postCode;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Invoice withCity(String city) {
		this.city = city;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public Invoice withCareOf(String careOf) {
		this.careOf = careOf;
		return this;

	}

	public Boolean getPdfAvailable() {
		return pdfAvailable;
	}

	public void setPdfAvailable(Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
	}

	public Invoice withPdfAvailable(Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(administration, amountVatExcluded, amountVatIncluded, careOf, city, currency,
				customerNumber, customerType, dueDate, facilityId, invoiceDate, invoiceDescription, invoiceName,
				invoiceNumber, invoiceStatus, invoiceType, ocrNumber, organizationGroup, organizationNumber,
				pdfAvailable, postCode, reversedVat, rounding, street, totalAmount, vat, vatEligibleAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(administration, other.administration)
				&& Objects.equals(amountVatExcluded, other.amountVatExcluded)
				&& Objects.equals(amountVatIncluded, other.amountVatIncluded) && Objects.equals(careOf, other.careOf)
				&& Objects.equals(city, other.city) && Objects.equals(currency, other.currency)
				&& Objects.equals(customerNumber, other.customerNumber) && customerType == other.customerType
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(facilityId, other.facilityId)
				&& Objects.equals(invoiceDate, other.invoiceDate)
				&& Objects.equals(invoiceDescription, other.invoiceDescription)
				&& Objects.equals(invoiceName, other.invoiceName) && Objects.equals(invoiceNumber, other.invoiceNumber)
				&& Objects.equals(invoiceStatus, other.invoiceStatus) && Objects.equals(invoiceType, other.invoiceType)
				&& Objects.equals(ocrNumber, other.ocrNumber)
				&& Objects.equals(organizationGroup, other.organizationGroup)
				&& Objects.equals(organizationNumber, other.organizationNumber)
				&& Objects.equals(pdfAvailable, other.pdfAvailable) && Objects.equals(postCode, other.postCode)
				&& Objects.equals(reversedVat, other.reversedVat) && Objects.equals(rounding, other.rounding)
				&& Objects.equals(street, other.street) && Objects.equals(totalAmount, other.totalAmount)
				&& Objects.equals(vat, other.vat) && Objects.equals(vatEligibleAmount, other.vatEligibleAmount);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [customerNumber=").append(customerNumber).append(", customerType=").append(customerType)
				.append(", facilityId=").append(facilityId).append(", invoiceNumber=").append(invoiceNumber)
				.append(", invoiceDate=").append(invoiceDate).append(", invoiceName=").append(invoiceName)
				.append(", invoiceType=").append(invoiceType).append(", invoiceDescription=").append(invoiceDescription)
				.append(", invoiceStatus=").append(invoiceStatus).append(", ocrNumber=").append(ocrNumber)
				.append(", dueDate=").append(dueDate).append(", totalAmount=").append(totalAmount)
				.append(", amountVatIncluded=").append(amountVatIncluded).append(", amountVatExcluded=")
				.append(amountVatExcluded).append(", vatEligibleAmount=").append(vatEligibleAmount)
				.append(", rounding=").append(rounding).append(", vat=").append(vat).append(", reversedVat=")
				.append(reversedVat).append(", currency=").append(currency).append(", organizationGroup=")
				.append(organizationGroup).append(", organizationNumber=").append(organizationNumber)
				.append(", administration=").append(administration).append(", street=").append(street)
				.append(", postCode=").append(postCode).append(", city=").append(city).append(", careOf=")
				.append(careOf).append(", pdfAvailable=").append(pdfAvailable).append("]");
		return builder.toString();
	}

}
