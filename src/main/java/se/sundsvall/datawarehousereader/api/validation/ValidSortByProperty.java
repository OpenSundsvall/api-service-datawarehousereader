package se.sundsvall.datawarehousereader.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import se.sundsvall.datawarehousereader.api.validation.impl.ValidSortByPropertyConstraintValidator;

@Documented
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSortByPropertyConstraintValidator.class)
public @interface ValidSortByProperty {
	String message() default "one or more of properties in list are not present in entity.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
