package se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AgreementEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(AgreementEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var uuid = "uuid";
		final var customerOrgId = "customerOrgId";
		final var customerId = 111222;
		final var facilityId = "facilityId";
		final var category = "category";
		final var billingId = 222333;
		final var agreementId = 333444;
		final var description = "description";
		final var mainAgreement = "mainAgreement";
		final var binding = "binding";
		final var bindingRule = "bindingRule";
		final var fromDate = LocalDateTime.now().minusDays(7);
		final var toDate = LocalDateTime.now();

		final var entity = AgreementEntity.create()
			.withUuid(uuid)
			.withCustomerOrgId(customerOrgId)
			.withCustomerId(customerId)
			.withFacilityId(facilityId)
			.withCategory(category)
			.withBillingId(billingId)
			.withAgreementId(agreementId)
			.withDescription(description)
			.withMainAgreement(mainAgreement)
			.withBinding(binding)
			.withBindingRule(bindingRule)
			.withFromDate(fromDate)
			.withToDate(toDate);

		assertThat(entity.getUuid()).isEqualTo(uuid);
		assertThat(entity.getCustomerOrgId()).isEqualTo(customerOrgId);
		assertThat(entity.getCustomerId()).isEqualTo(customerId);
		assertThat(entity.getFacilityId()).isEqualTo(facilityId);
		assertThat(entity.getCategory()).isEqualTo(category);
		assertThat(entity.getBillingId()).isEqualTo(billingId);
		assertThat(entity.getAgreementId()).isEqualTo(agreementId);
		assertThat(entity.getDescription()).isEqualTo(description);
		assertThat(entity.getMainAgreement()).isEqualTo(mainAgreement);
		assertThat(entity.getBinding()).isEqualTo(binding);
		assertThat(entity.getBindingRule()).isEqualTo(bindingRule);
		assertThat(entity.getFromDate()).isEqualTo(fromDate);
		assertThat(entity.getToDate()).isEqualTo(toDate);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(AgreementEntity.create()).hasAllNullFieldsOrProperties();
		assertThat(new AgreementEntity()).hasAllNullFieldsOrProperties();
	}
}
