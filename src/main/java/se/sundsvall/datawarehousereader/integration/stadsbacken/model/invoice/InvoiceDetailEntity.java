package se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "kundinfo", name = "vInvoiceDetail")
public class InvoiceDetailEntity {

	@Id
	@Column(name = "invoiceProductSeq", nullable = false, insertable = false, updatable = false)
	private int invoiceProductSeq;
	 
	@Column(name = "invoiceId", nullable = false, insertable = false, updatable = false)
	private int invoiceId;

	@Column(name = "Productcode", nullable = false, insertable = false, updatable = false, columnDefinition = "smallint")
	private int productCode;

	@Column(name = "periodFrom", insertable = false, updatable = false, columnDefinition = "nvarchar(4000)")
	private String periodFrom;

	@Column(name = "periodTo", insertable = false, updatable = false, columnDefinition = "nvarchar(4000)")
	private String periodTo;

	@Column(name = "Amount", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal amount;

	@Column(name = "InvoiceNumber", insertable = false, updatable = false)
	private Long invoiceNumber;

	@Column(name = "AmountVatExcluded", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal amountVatExcluded;

	@Column(name = "Vat", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal vat;

	@Column(name = "Vatrate", insertable = false, updatable = false)
	private Double vatRate;

	@Column(name = "Quantity", insertable = false, updatable = false)
	private Double quantity;

	@Column(name = "unit", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String unit;

	@Column(name = "Unitprice", insertable = false, updatable = false, columnDefinition = "money")
	private BigDecimal unitPrice;

	@Column(name = "Description", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String description;

	@Column(name = "ProductName", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String productName;

	@Column(name = "OrganizationId", insertable = false, updatable = false, columnDefinition = "varchar(10)")
	private String organizationId;

	public int getInvoiceProductSeq() {
		return invoiceProductSeq;
	}

	public void setInvoiceProductSeq(int invoiceProductSeq) {
		this.invoiceProductSeq = invoiceProductSeq;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public Double getVatRate() {
		return vatRate;
	}

	public void setVatRate(Double vatRate) {
		this.vatRate = vatRate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}

	public String getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountVatExcluded, description, invoiceId, invoiceNumber, invoiceProductSeq, organizationId, periodFrom, periodTo, productCode, productName, quantity, unit, unitPrice, vat, vatRate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceDetailEntity other = (InvoiceDetailEntity) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(amountVatExcluded, other.amountVatExcluded) && Objects.equals(description, other.description) && invoiceId == other.invoiceId && Objects.equals(invoiceNumber, other.invoiceNumber)
			&& invoiceProductSeq == other.invoiceProductSeq && Objects.equals(organizationId, other.organizationId) && Objects.equals(periodFrom, other.periodFrom) && Objects.equals(periodTo, other.periodTo) && productCode == other.productCode
			&& Objects.equals(productName, other.productName) && Objects.equals(quantity, other.quantity) && Objects.equals(unit, other.unit) && Objects.equals(unitPrice, other.unitPrice) && Objects.equals(vat, other.vat) && Objects.equals(vatRate,
				other.vatRate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetailEntity [invoiceProductSeq=").append(invoiceProductSeq).append(", invoiceId=").append(invoiceId).append(", productCode=").append(productCode).append(", periodFrom=").append(periodFrom).append(", periodTo=").append(
			periodTo).append(", amount=").append(amount).append(", invoiceNumber=").append(invoiceNumber).append(", amountVatExcluded=").append(amountVatExcluded).append(", vat=").append(vat).append(", vatRate=").append(vatRate).append(", quantity=")
			.append(quantity).append(", unit=").append(unit).append(", unitPrice=").append(unitPrice).append(", description=").append(description).append(", productName=").append(productName).append(", organizationId=").append(organizationId).append(
				"]");
		return builder.toString();
	}
}
