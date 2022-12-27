package se.sundsvall.datawarehousereader.api.validation.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import se.sundsvall.datawarehousereader.api.model.AbstractParameterBase;
import se.sundsvall.datawarehousereader.api.model.agreement.AgreementParameters;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagementParameters;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.api.validation.ValidSortByProperty;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.agreement.AgreementEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityDayEntity;

public class ValidSortByPropertyConstraintValidator implements ConstraintValidator<ValidSortByProperty, AbstractParameterBase> {
	private static final String CUSTOM_ERROR_MESSAGE_TEMPLATE = "One or more of the sortBy members %s are not valid. Valid properties to sort by are %s.";

	private Map<Class<? extends AbstractParameterBase>, Class<?>> parameterToEntityMapping = new HashMap<>();
	private Map<Class<?>, List<String>> entityProperties = new HashMap<>();

	public ValidSortByPropertyConstraintValidator() {
		parameterToEntityMapping.put(AgreementParameters.class, AgreementEntity.class);
		parameterToEntityMapping.put(CustomerEngagementParameters.class, CustomerEntity.class);
		parameterToEntityMapping.put(InstalledBaseParameters.class, InstalledBaseItemEntity.class);
		parameterToEntityMapping.put(InvoiceParameters.class, InvoiceEntity.class);
		parameterToEntityMapping.put(MeasurementParameters.class, MeasurementElectricityDayEntity.class);

		for (Class<?> clazz : parameterToEntityMapping.values()) {
			entityProperties.put(clazz, Stream.of(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Column.class))
				.map(Field::getName)
				.toList());
		}
	}
	
	@Override
	public boolean isValid(final AbstractParameterBase parameters, final ConstraintValidatorContext context) {
		boolean isValid = isEmpty(parameters.getSortBy()) || !parameterToEntityMapping.containsKey(parameters.getClass()) || 
			entityProperties.get(parameterToEntityMapping.get(parameters.getClass())).containsAll(parameters.getSortBy());
		
		if (!isValid) {
			useCustomMessageForValidation(context, parameterToEntityMapping.get(parameters.getClass()), parameters.getSortBy());
		}
		
		return isValid;
	}

	private void useCustomMessageForValidation(ConstraintValidatorContext constraintContext, Class<?> clazz, List<String> sortBy) {
		constraintContext.disableDefaultConstraintViolation();
		constraintContext.buildConstraintViolationWithTemplate(String.format(CUSTOM_ERROR_MESSAGE_TEMPLATE, sortBy, entityProperties.get(clazz))).addConstraintViolation();
	}
}
