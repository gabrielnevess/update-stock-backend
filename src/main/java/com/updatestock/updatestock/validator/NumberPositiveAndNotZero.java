package com.updatestock.updatestock.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = NumberPositiveAndNotZero.NumberPositiveAndNotZeroValidator.class)
@Documented
public @interface NumberPositiveAndNotZero {

    public String message() default "Quantidade deve ser positivo e maior que zero.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    class NumberPositiveAndNotZeroValidator implements ConstraintValidator<NumberPositiveAndNotZero, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            if(value != null && value <= 0) {
                return false;
            }
            return true;
        }

    }

}