package se.sundsvall.datawarehousereader.api.model.invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.datawarehousereader.api.model.CustomerType;

@Schema(description = "Invoice request parameters model")
public class InvoiceParameters extends AbstractParameterBase {

	private static final List<String> DEFAULT_SORT_BY_PROPERTY = List.of("invoiceDate");

	@ArraySchema(schema = @Schema(description = "Customer numbers", example = "39195"))
	private List<String> customerNumber;

	@Schema(implementation = CustomerType.class)
	private CustomerType customerType;

	@ArraySchema(schema = @Schema(description = "Facility ids", example = "735999109151401011"))
	private List<String> facilityId;

	@Schema(description = "Invoice number", example = "767915994")
	private Long invoiceNumber;

	@Schema(description = "Earliest invoice date. Format is YYYY-MM-DD.", example = "2022-01-01")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate invoiceDateFrom;

	@Schema(description = "Latest invoice date. Format is YYYY-MM-DD.", example = "2022-01-31")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate invoiceDateTo;

	@Schema(description = "Invoice name", example = "765801493.pdf")
	private String invoiceName;

	@Schema(description = "Invoice type", example = "Faktura")
	private String invoiceType;

	@Schema(description = "Invoice status", example = "Skickad")
	private String invoiceStatus;

	@Schema(description = "Ocr number", example = "767915994")
	private Long ocrNumber;

	@Schema(description = "Earliest due date. Format is YYYY-MM-DD.", example = "2022-01-01")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dueDateFrom;

	@Schema(description = "Latest due date. Format is YYYY-MM-DD.", example = "2022-01-31")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dueDateTo;

	@Schema(description = "Organization group", example = "stadsbacken")
	private String organizationGroup;

	@Schema(description = "Organization number of invoice issuer", example = "5565027223")
	private String organizationNumber;

	@Schema(description = "Adminstration", example = "Sundsvall Elnät")
	private String administration;

	public InvoiceParameters() {
		this.sortBy = DEFAULT_SORT_BY_PROPERTY;
	}

	public static InvoiceParameters create() {
		return new InvoiceParameters();
	}

	public List<String> getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(List<String> customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public List<String> getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(List<String> facilityId) {
		this.facilityId = facilityId;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public LocalDate getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
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

	public LocalDate getDueDateFrom() {
		return dueDateFrom;
	}

	public void setDueDateFrom(LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public LocalDate getDueDateTo() {
		return dueDateTo;
	}

	public void setDueDateTo(LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(administration, customerNumber, customerType, dueDateFrom, dueDateTo,
			facilityId, invoiceDateFrom, invoiceDateTo, invoiceName, invoiceNumber, invoiceStatus, invoiceType,
			ocrNumber, organizationGroup, organizationNumber);
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
		InvoiceParameters other = (InvoiceParameters) obj;
		return Objects.equals(administration, other.administration)
			&& Objects.equals(customerNumber, other.customerNumber) && customerType == other.customerType
			&& Objects.equals(dueDateFrom, other.dueDateFrom) && Objects.equals(dueDateTo, other.dueDateTo)
			&& Objects.equals(facilityId, other.facilityId)
			&& Objects.equals(invoiceDateFrom, other.invoiceDateFrom)
			&& Objects.equals(invoiceDateTo, other.invoiceDateTo) && Objects.equals(invoiceName, other.invoiceName)
			&& Objects.equals(invoiceNumber, other.invoiceNumber)
			&& Objects.equals(invoiceStatus, other.invoiceStatus) && Objects.equals(invoiceType, other.invoiceType)
			&& Objects.equals(ocrNumber, other.ocrNumber)
			&& Objects.equals(organizationGroup, other.organizationGroup)
			&& Objects.equals(organizationNumber, other.organizationNumber);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceParameters [customerNumber=").append(customerNumber).append(", customerType=").append(customerType).append(", facilityId=").append(facilityId).append(", invoiceNumber=").append(invoiceNumber).append(
			", invoiceDateFrom=").append(invoiceDateFrom).append(", invoiceDateTo=").append(invoiceDateTo).append(", invoiceName=").append(invoiceName).append(", invoiceType=").append(invoiceType).append(", invoiceStatus=").append(invoiceStatus)
			.append(", ocrNumber=").append(ocrNumber).append(", dueDateFrom=").append(dueDateFrom).append(", dueDateTo=").append(dueDateTo).append(", organizationGroup=").append(organizationGroup).append(", organizationNumber=").append(
				organizationNumber).append(", administration=").append(administration).append(", page=").append(page).append(", limit=").append(limit).append(", sortBy=").append(sortBy).append(", sortDirection=").append(sortDirection).append("]");
		return builder.toString();
	}
}
