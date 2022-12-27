package se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstalledBaseItemMetaDataEmbeddable implements Serializable {
	private static final long serialVersionUID = 7581230689748313918L;

	@Column(name = "company", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String company;

	@Column(name = "`key`", nullable = false, insertable = false, updatable = false)
	private String key;

	@Column(name = "`value`", insertable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String value;

	@Column(name = "`type`", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(8)")
	private String type;

	@Column(name = "displayName", nullable = false, insertable = false, updatable = false, columnDefinition = "varchar(26)")
	private String displayName;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, displayName, key, type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstalledBaseItemMetaDataEmbeddable other = (InstalledBaseItemMetaDataEmbeddable) obj;
		return Objects.equals(company, other.company) && Objects.equals(displayName, other.displayName)
				&& Objects.equals(key, other.key) && Objects.equals(type, other.type)
				&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstalledBaseItemMetaDataEmbeddable [company=").append(company).append(", key=").append(key)
				.append(", value=").append(value).append(", type=").append(type).append(", displayName=")
				.append(displayName).append("]");
		return builder.toString();
	}
}
