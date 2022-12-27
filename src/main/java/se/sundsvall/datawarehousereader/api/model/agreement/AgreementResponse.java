package se.sundsvall.datawarehousereader.api.model.agreement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.MetaData;

import java.util.List;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Schema(description = "Agreement response model")
public class AgreementResponse {

	@JsonProperty("_meta")
	@Schema(implementation = MetaData.class, accessMode = READ_ONLY)
	private MetaData metaData;

	@ArraySchema(schema = @Schema(implementation = Agreement.class, accessMode = READ_ONLY))
	private List<Agreement> agreements;

	public static AgreementResponse create() {
		return new AgreementResponse();
	}
	
	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public AgreementResponse withMetaData(MetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	public List<Agreement> getAgreements() {
		return agreements;
	}
	
	public void setAgreements(List<Agreement> agreements) {
		this.agreements = agreements;
	}

	public AgreementResponse withAgreements(List<Agreement> agreements) {
		this.agreements = agreements;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agreements, metaData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgreementResponse other = (AgreementResponse) obj;
		return Objects.equals(agreements, other.agreements) && Objects.equals(metaData, other.metaData);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgreementResponse [metaData=").append(metaData).append(", agreements=").append(agreements)
				.append("]");
		return builder.toString();
	}
	
}
