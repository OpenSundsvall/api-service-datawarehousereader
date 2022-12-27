package se.sundsvall.datawarehousereader.api.model;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.stream.Stream;

import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer type", enumAsRef = true, example = "Enterprise")
public enum CustomerType {
	ENTERPRISE("Enterprise"),
	PRIVATE("Private");

	private final String stadsbackenTranslation;

	CustomerType(String stadsbackenTranslation) {
		this.stadsbackenTranslation = stadsbackenTranslation;
	}

	public static CustomerType fromValue(String value, Status statusCode, String statusText) {
		if (isBlank(value)) {
			return null;
		}

		return Stream.of(values())
			.filter(member -> member.stadsbackenTranslation.equalsIgnoreCase(value.trim()))
			.findAny()
			.orElseThrow(() -> Problem.valueOf(statusCode, format(statusText, value)));
	}

	@JsonValue
	public String getStadsbackenTranslation() {
		return stadsbackenTranslation;
	}
}
