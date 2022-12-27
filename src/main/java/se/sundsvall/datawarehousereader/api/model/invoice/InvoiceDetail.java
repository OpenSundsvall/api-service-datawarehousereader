package se.sundsvall.datawarehousereader.api.model.invoice;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.math.BigDecimal;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Invoice detail model")
public class InvoiceDetail {

	@Schema(description = "Invoice number", example = "767915994", accessMode = READ_ONLY)
	private Long invoiceNumber;

	@Schema(description = "Amount", example = "66.97", accessMode = READ_ONLY)
	private BigDecimal amount;

	@Schema(description = "Amount excluded VAT", example = "53.57", accessMode = READ_ONLY)
	private BigDecimal amountVatExcluded;

	@Schema(description = "VAT", example = "13.40", accessMode = READ_ONLY)
	private BigDecimal vat;

	@Schema(description = "VAT rate", example = "25.0", accessMode = READ_ONLY)
	private Double vatRate;

	@Schema(description = "Quantity", example = "154.39", accessMode = READ_ONLY)
	private Double quantity;

	@Schema(description = "Unit", example = "kWh", accessMode = READ_ONLY)
	private String unit;

	@Schema(description = "Price per unit", example = "0.347", accessMode = READ_ONLY)
	private BigDecimal unitPrice;

	@Schema(description = "Period from", example = "2022-01-01", accessMode = READ_ONLY)
	private String periodFrom;

	@Schema(description = "Period to", example = "2022-01-31", accessMode = READ_ONLY)
	private String periodTo;

	@Schema(description = "Description", accessMode = READ_ONLY)
	private String description;

	@Schema(description = "Product code", example = "1404", accessMode = READ_ONLY)
	private Integer productCode;

	@Schema(description = "Product name", example = "Elöverföring", accessMode = READ_ONLY)
	private String productName;

	@Schema(description = "Organization number of invoice issuer", example = "5565027223", accessMode = READ_ONLY)
	private String organizationNumber;

	public static InvoiceDetail create() {
		return new InvoiceDetail();
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public InvoiceDetail withInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public InvoiceDetail withAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public BigDecimal getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public InvoiceDetail withAmountVatExcluded(BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public InvoiceDetail withVat(BigDecimal vat) {
		this.vat = vat;
		return this;
	}

	public Double getVatRate() {
		return vatRate;
	}

	public void setVatRate(Double vatRate) {
		this.vatRate = vatRate;
	}

	public InvoiceDetail withVatRate(Double vatRate) {
		this.vatRate = vatRate;
		return this;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public InvoiceDetail withQuantity(Double quantity) {
		this.quantity = quantity;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public InvoiceDetail withUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceDetail withUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public String getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}

	public InvoiceDetail withPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
		return this;
	}

	public String getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}

	public InvoiceDetail withPeriodTo(String periodTo) {
		this.periodTo = periodTo;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InvoiceDetail withDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getProductCode() {
		return productCode;
	}

	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}

	public InvoiceDetail withProductCode(Integer productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public InvoiceDetail withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public InvoiceDetail withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountVatExcluded, description, invoiceNumber, organizationNumber, periodFrom, periodTo, productCode, productName, quantity, unit, unitPrice, vat, vatRate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceDetail other = (InvoiceDetail) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(amountVatExcluded, other.amountVatExcluded) && Objects.equals(description, other.description) && Objects.equals(invoiceNumber, other.invoiceNumber) && Objects.equals(
			organizationNumber, other.organizationNumber) && Objects.equals(periodFrom, other.periodFrom) && Objects.equals(periodTo, other.periodTo) && Objects.equals(productCode, other.productCode) && Objects.equals(productName, other.productName)
			&& Objects.equals(quantity, other.quantity) && Objects.equals(unit, other.unit) && Objects.equals(unitPrice, other.unitPrice) && Objects.equals(vat, other.vat) && Objects.equals(vatRate, other.vatRate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetail [invoiceNumber=").append(invoiceNumber).append(", amount=").append(amount).append(", amountVatExcluded=").append(amountVatExcluded).append(", vat=").append(vat).append(", vatRate=").append(vatRate).append(
			", quantity=").append(quantity).append(", unit=").append(unit).append(", unitPrice=").append(unitPrice).append(", periodFrom=").append(periodFrom).append(", periodTo=").append(periodTo).append(", description=").append(description).append(
				", productCode=").append(productCode).append(", productName=").append(productName).append(", organizationNumber=").append(organizationNumber).append("]");
		return builder.toString();
	}
}
