package se.sundsvall.datawarehousereader.api.validation.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;

@ExtendWith(MockitoExtension.class)
class ValidSortByPropertyConstraintValidatorTest {

	@Mock
	private ConstraintValidatorContext constraintValidatorContextMock;

	@Mock
	private ConstraintViolationBuilder constraintViolationBuilderMock;
	
	@InjectMocks
	private ValidSortByPropertyConstraintValidator validator;
	
	@Test
	void testValidSortByProperties() {
		final var parameters = AgreementParameters.create();
		parameters.setSortBy(List.of("agreementId", "mainAgreement", "fromDate"));
		
		assertThat(validator.isValid(parameters, constraintValidatorContextMock)).isTrue();
		
		verifyNoInteractions(constraintValidatorContextMock, constraintViolationBuilderMock);
	}
	
	@Test
	void testWithNoSortByProperties() {
		assertThat(validator.isValid(AgreementParameters.create(), constraintValidatorContextMock)).isTrue();
		
		verifyNoInteractions(constraintValidatorContextMock, constraintViolationBuilderMock);
	}

	@Test
	void testWithNotMappedParameterBase() {
		final var parameters = new NotMappedParameterBase();
		parameters.setSortBy(List.of("should-not-matter"));
		
		assertThat(validator.isValid(parameters, constraintValidatorContextMock)).isTrue();

		verifyNoInteractions(constraintValidatorContextMock, constraintViolationBuilderMock);
	}

	@Test
	void testInvalidSortByProperties() {
		final var parameters = AgreementParameters.create();
		parameters.setSortBy(List.of("agreementId", "notValid", "fromDate"));

		when(constraintValidatorContextMock.buildConstraintViolationWithTemplate(any())).thenReturn(constraintViolationBuilderMock);
		
		assertThat(validator.isValid(parameters, constraintValidatorContextMock)).isFalse();
		
		verify(constraintValidatorContextMock).disableDefaultConstraintViolation();
		verify(constraintValidatorContextMock).buildConstraintViolationWithTemplate("""
			One or more of the sortBy members [agreementId, notValid, fromDate] are not valid. Valid properties to sort by are \
			[agreementId, billingId, uuid, customerOrgId, customerId, facilityId, category, description, mainAgreement, binding, \
			bindingRule, fromDate, toDate].""");
		verify(constraintViolationBuilderMock).addConstraintViolation();
	}
	
	// Test class for testing parameter class not mapped in validator
	private static class NotMappedParameterBase extends AbstractParameterBase {
		
	}
}
