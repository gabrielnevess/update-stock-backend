package com.updatestock.updatestock.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

@Constraint(validatedBy = ValidateUniqueEmail.UniqueUserValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUniqueEmail {

    String message() default "E-mail já está em uso!";
    String fieldEmail();
    String fieldId();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface EmailValidate {
        ValidateUniqueEmail value();
    }

    public class UniqueUserValidator implements ConstraintValidator<ValidateUniqueEmail, Object> {
        private String fieldEmail;
        private String fieldId;

        public void initialize(ValidateUniqueEmail constraintAnnotation) {
            this.fieldEmail = constraintAnnotation.fieldEmail();
            this.fieldId = constraintAnnotation.fieldId();
        }

        public boolean isValid(Object value, ConstraintValidatorContext context) {
            Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(fieldEmail);
            Object fieldIdValue = new BeanWrapperImpl(value).getPropertyValue(fieldId);

            if(fieldValue != null) {
                
            }
            if(fieldIdValue != null) {

            }
            // if (fieldValue != null) {
            //     return fieldValue.equals(fieldIdValue);
            // } else {
            //     return fieldIdValue == null;
            // }

            return false;

        }
    }

}
