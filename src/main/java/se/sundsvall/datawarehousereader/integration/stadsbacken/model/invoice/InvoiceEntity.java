package se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "kundinfo", name = "vInvoice")
public class InvoiceEntity {

	@Id
	@Column(name = "InvoiceNumber", nullable = false, insertable = false, updatable = false)
	private long invoiceNumber;

	@Column(name = "InvoiceDate", insertable = false, updatable = false)
	private LocalDate invoiceDate;

	@Column(name = "InvoiceName", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(28)")
	private String invoiceName;

	@Column(name = "InvoiceType", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String invoiceType;

	@Column(name = "InvoiceDescription", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String invoiceDescription;

	@Column(name = "InvoiceStatus", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String invoiceStatus;

	@Column(name = "OcrNumber", insertable = false, updatable = false)
	private Long ocrNumber;

	@Column(name = "DueDate", insertable = false, updatable = false)
	private LocalDate dueDate;

	@Column(name = "TotalAmount", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal totalAmount;

	@Column(name = "AmountVatIncluded", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal amountVatIncluded;

	@Column(name = "AmountVatExcluded", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal amountVatExcluded;

	@Column(name = "VatEligiblaAmount", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal vatEligibleAmount;

	@Column(name = "Rounding", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal rounding;

	@Column(name = "Vat", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal vat;

	@Column(name = "ReversedVat", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(5)")
	private Boolean reversedVat;

	@Column(name = "Currency", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(3)")
	private String currency;

	@Column(name = "CustomerId", nullable = false, insertable = false, updatable = false)
	private int customerId;

	@Column(name = "CustomerType", insertable = false, updatable = false, columnDefinition = "varchar(10)")
	private String customerType;

	@Column(name = "FacilityId", insertable = false, updatable = false)
	private String facilityId;

	@Column(name = "OrganizationGroup", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(11)")
	private String organizationGroup;

	@Column(name = "Administration", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String administration;

	@Column(name = "OrganizationId", insertable = false, updatable = false, columnDefinition = "varchar(10)")
	private String organizationId;
	
	@Column(name = "Street", insertable = false, updatable = false)
	private String street;

	@Column(name = "PostalCode", insertable = false, updatable = false)
	private String postCode;

	@Column(name = "City", insertable = false, updatable = false)
	private String city;

	@Column(name = "CareOf", insertable = false, updatable = false)
	private String careOf;

	@Column(name = "PdfAvailable", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(5)")
	private Boolean pdfAvailable;

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(Long ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAmountVatIncluded() {
		return amountVatIncluded;
	}

	public void setAmountVatIncluded(BigDecimal amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
	}

	public BigDecimal getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public BigDecimal getVatEligibleAmount() {
		return vatEligibleAmount;
	}

	public void setVatEligibleAmount(BigDecimal vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
	}

	public BigDecimal getRounding() {
		return rounding;
	}

	public void setRounding(BigDecimal rounding) {
		this.rounding = rounding;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public Boolean getReversedVat() {
		return reversedVat;
	}

	public void setReversedVat(Boolean reversedVat) {
		this.reversedVat = reversedVat;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public Boolean getPdfAvailable() {
		return pdfAvailable;
	}

	public void setPdfAvailable(Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(administration, amountVatExcluded, amountVatIncluded, careOf, city, currency, customerId,
				customerType, dueDate, facilityId, invoiceDate, invoiceDescription, invoiceName, invoiceNumber,
				invoiceStatus, invoiceType, ocrNumber, organizationGroup, organizationId, pdfAvailable, postCode,
				reversedVat, rounding, street, totalAmount, vat, vatEligibleAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceEntity other = (InvoiceEntity) obj;
		return Objects.equals(administration, other.administration)
				&& Objects.equals(amountVatExcluded, other.amountVatExcluded)
				&& Objects.equals(amountVatIncluded, other.amountVatIncluded) && Objects.equals(careOf, other.careOf)
				&& Objects.equals(city, other.city) && Objects.equals(currency, other.currency)
				&& customerId == other.customerId && Objects.equals(customerType, other.customerType)
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(facilityId, other.facilityId)
				&& Objects.equals(invoiceDate, other.invoiceDate)
				&& Objects.equals(invoiceDescription, other.invoiceDescription)
				&& Objects.equals(invoiceName, other.invoiceName) && invoiceNumber == other.invoiceNumber
				&& Objects.equals(invoiceStatus, other.invoiceStatus) && Objects.equals(invoiceType, other.invoiceType)
				&& Objects.equals(ocrNumber, other.ocrNumber)
				&& Objects.equals(organizationGroup, other.organizationGroup)
				&& Objects.equals(organizationId, other.organizationId)
				&& Objects.equals(pdfAvailable, other.pdfAvailable) && Objects.equals(postCode, other.postCode)
				&& Objects.equals(reversedVat, other.reversedVat) && Objects.equals(rounding, other.rounding)
				&& Objects.equals(street, other.street) && Objects.equals(totalAmount, other.totalAmount)
				&& Objects.equals(vat, other.vat) && Objects.equals(vatEligibleAmount, other.vatEligibleAmount);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceEntity [invoiceNumber=").append(invoiceNumber).append(", invoiceDate=")
				.append(invoiceDate).append(", invoiceName=").append(invoiceName).append(", invoiceType=")
				.append(invoiceType).append(", invoiceDescription=").append(invoiceDescription)
				.append(", invoiceStatus=").append(invoiceStatus).append(", ocrNumber=").append(ocrNumber)
				.append(", dueDate=").append(dueDate).append(", totalAmount=").append(totalAmount)
				.append(", amountVatIncluded=").append(amountVatIncluded).append(", amountVatExcluded=")
				.append(amountVatExcluded).append(", vatEligibleAmount=").append(vatEligibleAmount)
				.append(", rounding=").append(rounding).append(", vat=").append(vat).append(", reversedVat=")
				.append(reversedVat).append(", currency=").append(currency).append(", customerId=").append(customerId)
				.append(", customerType=").append(customerType).append(", facilityId=").append(facilityId)
				.append(", organizationGroup=").append(organizationGroup).append(", administration=")
				.append(administration).append(", organizationId=").append(organizationId).append(", street=")
				.append(street).append(", postCode=").append(postCode).append(", city=").append(city)
				.append(", careOf=").append(careOf).append(", pdfAvailable=").append(pdfAvailable).append("]");
		return builder.toString();
	}
}
