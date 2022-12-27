package se.sundsvall.datawarehousereader.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static se.sundsvall.datawarehousereader.Constants.INVALID_PARAMETER_CUSTOMER_TYPE;
import static se.sundsvall.datawarehousereader.Constants.UNKNOWN_CUSTOMER_TYPE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.zalando.problem.ThrowableProblem;

class CustomerTypeTest {

	@Test
	void testValidEnumValues() {
		for (CustomerType customerType : CustomerType.values()) {
			assertThat(customerType).isEqualTo(CustomerType.fromValue(customerType.name(), null, null));
		}
	}

	@Test
	void testValidEnumValuesWithDifferentCaption() {
		for (CustomerType customerType : CustomerType.values()) {
			assertThat(customerType).isEqualTo(CustomerType.fromValue(customerType.name().toLowerCase(), null, null));
		}
	}

	@Test
	void testValidEnumValuesWithLeadingAndTrailingSpaces() {
		for (CustomerType customerType : CustomerType.values()) {
			assertThat(customerType).isEqualTo(CustomerType.fromValue(" ".concat(customerType.name().concat(" ")), null, null));
		}
	}

	@Test
	void testNullValue() {
		assertThat(CustomerType.fromValue(null, null, null)).isNull();
	}

	@ParameterizedTest
	@ValueSource(strings = { "", " ", "	" })
	void testEmptyValues(String value) {
		assertThat(CustomerType.fromValue(value, null, null)).isNull();
	}

	@Test
	void testUnknownJsonValue() {
		final var exception = assertThrows(ThrowableProblem.class,
			() -> CustomerType.fromValue("UNKNOWN", BAD_REQUEST, INVALID_PARAMETER_CUSTOMER_TYPE));

		assertThat(exception.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(exception.getMessage()).isEqualTo("Bad Request: Invalid value for enum CustomerType: UNKNOWN");
	}

	@Test
	void testUnknownDbValue() {
		final var exception = assertThrows(ThrowableProblem.class,
			() -> CustomerType.fromValue("UNKNOWN", INTERNAL_SERVER_ERROR, UNKNOWN_CUSTOMER_TYPE));

		assertThat(exception.getStatus()).isEqualTo(INTERNAL_SERVER_ERROR);
		assertThat(exception.getMessage()).isEqualTo("Internal Server Error: Customer repository result contains an unknown value for enum CustomerType: UNKNOWN");
	}

	@Test
	void testStadsbackenTranslation() {
		assertThat(CustomerType.ENTERPRISE.getStadsbackenTranslation()).isEqualTo("Enterprise");
		assertThat(CustomerType.PRIVATE.getStadsbackenTranslation()).isEqualTo("Private");
	}
}
