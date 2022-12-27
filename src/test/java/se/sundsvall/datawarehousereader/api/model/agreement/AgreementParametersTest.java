package se.sundsvall.datawarehousereader.api.model.agreement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.api.model.Category.WATER;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class AgreementParametersTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(AgreementParameters.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testCreatePattern() {
		final var customerNumber = "customerNumber";
		final var partyId = "partyId";
		final var categories = List.of(ELECTRICITY, WATER);
		final var billingId = "billingId";
		final var facilityId = "facilityId";
		final var agreementId = "agreementId";
		final var description = "description";
		final var mainAgreement = false;
		final var binding = true;
		final var bindingRule = "bindingRule";
		final var fromDate = LocalDate.now().minusYears(1L);
		final var toDate = LocalDate.now();

		final var agreementParameters = AgreementParameters.create()
			.withCustomerNumber(customerNumber)
			.withPartyId(partyId)
			.withCategory(categories)
			.withBillingId(billingId)
			.withFacilityId(facilityId)
			.withAgreementId(agreementId)
			.withDescription(description)
			.withMainAgreement(mainAgreement)
			.withBinding(binding)
			.withBindingRule(bindingRule)
			.withFromDate(fromDate)
			.withToDate(toDate);

		assertThat(agreementParameters.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(agreementParameters.getPartyId()).isEqualTo(partyId);
		assertThat(agreementParameters.getCategory()).isEqualTo(categories);
		assertThat(agreementParameters.getBillingId()).isEqualTo(billingId);
		assertThat(agreementParameters.getFacilityId()).isEqualTo(facilityId);
		assertThat(agreementParameters.getAgreementId()).isEqualTo(agreementId);
		assertThat(agreementParameters.getDescription()).isEqualTo(description);
		assertThat(agreementParameters.getMainAgreement()).isEqualTo(mainAgreement);
		assertThat(agreementParameters.getBinding()).isEqualTo(binding);
		assertThat(agreementParameters.getBindingRule()).isEqualTo(bindingRule);
		assertThat(agreementParameters.getFromDate()).isEqualTo(fromDate);
		assertThat(agreementParameters.getToDate()).isEqualTo(toDate);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(AgreementParameters.create())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortDirection")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100)
			.hasFieldOrPropertyWithValue("sortDirection", Sort.DEFAULT_DIRECTION);

		assertThat(new AgreementParameters())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortDirection")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100)
			.hasFieldOrPropertyWithValue("sortDirection", Sort.DEFAULT_DIRECTION);
	}
}
