package se.sundsvall.datawarehousereader.api.model.invoice;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.MetaData;

@Schema(description = "Invoice response model")
public class InvoiceResponse {

	@JsonProperty("_meta")
	@Schema(implementation = MetaData.class, accessMode = READ_ONLY)
	private MetaData metaData;

	@ArraySchema(schema = @Schema(implementation = Invoice.class, accessMode = READ_ONLY))
	private List<Invoice> invoices;

	public static InvoiceResponse create() {
		return new InvoiceResponse();
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public InvoiceResponse withInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
		return this;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public InvoiceResponse withMetaData(MetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(invoices, metaData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceResponse other = (InvoiceResponse) obj;
		return Objects.equals(invoices, other.invoices) && Objects.equals(metaData, other.metaData);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceResponse [metaData=").append(metaData).append(", invoices=").append(invoices)
				.append("]");
		return builder.toString();
	}
}
