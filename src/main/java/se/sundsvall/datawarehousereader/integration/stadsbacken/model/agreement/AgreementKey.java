package se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement;

import java.io.Serializable;
import java.util.Objects;

public class AgreementKey implements Serializable {

	private static final long serialVersionUID = 6445265507256334621L;

	private Integer agreementId;

	private Integer billingId;

	public Integer getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Integer agreementId) {
		this.agreementId = agreementId;
	}

	public Integer getBillingId() {
		return billingId;
	}

	public void setBillingId(Integer billingId) {
		this.billingId = billingId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AgreementKey that = (AgreementKey) o;
		return Objects.equals(agreementId, that.agreementId) && Objects.equals(billingId, that.billingId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(agreementId, billingId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgreementKey [agreementId=").append(agreementId).append(", billingId=").append(billingId)
			.append("]");
		return builder.toString();
	}
}
