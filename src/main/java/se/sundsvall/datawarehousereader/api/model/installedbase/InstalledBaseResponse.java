package se.sundsvall.datawarehousereader.api.model.installedbase;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.MetaData;

@Schema(description = "Installed base response model")
public class InstalledBaseResponse {

	@JsonProperty("_meta")
	@Schema(implementation = MetaData.class, accessMode = READ_ONLY)
	private MetaData metaData;

	@ArraySchema(schema = @Schema(implementation = InstalledBaseItem.class, accessMode = READ_ONLY))
	private List<InstalledBaseItem> installedBase;

	public static InstalledBaseResponse create() {
		return new InstalledBaseResponse();
	}
	
	public List<InstalledBaseItem> getInstalledBase() {
		return installedBase;
	}
	
	public void setInstalledBase(List<InstalledBaseItem> installedBase) {
		this.installedBase = installedBase;
	}

	public InstalledBaseResponse withInstalledBase(List<InstalledBaseItem> installedBase) {
		this.installedBase = installedBase;
		return this;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public InstalledBaseResponse withMetaData(MetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(installedBase, metaData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstalledBaseResponse other = (InstalledBaseResponse) obj;
		return Objects.equals(installedBase, other.installedBase) && Objects.equals(metaData, other.metaData);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstalledBaseResponse [metaData=").append(metaData).append(", installedBase=")
				.append(installedBase).append("]");
		return builder.toString();
	}
}
