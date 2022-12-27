package se.sundsvall.datawarehousereader.api.model.measurement;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.model.MetaData;

@Schema(description = "Measurement response model")
public class MeasurementResponse {

	@JsonProperty("_meta")
	@Schema(implementation = MetaData.class, accessMode = READ_ONLY)
	private MetaData metaData;

	@ArraySchema(schema = @Schema(implementation = Measurement.class, accessMode = READ_ONLY))
	private List<Measurement> measurements;

	public static MeasurementResponse create() {
		return new MeasurementResponse();
	}
	
	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public MeasurementResponse withMetaData(MetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	public MeasurementResponse withMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(measurements, metaData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasurementResponse other = (MeasurementResponse) obj;
		return Objects.equals(measurements, other.measurements) && Objects.equals(metaData, other.metaData);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasurementResponse [metaData=").append(metaData).append(", measurements=").append(measurements)
				.append("]");
		return builder.toString();
	}
}
