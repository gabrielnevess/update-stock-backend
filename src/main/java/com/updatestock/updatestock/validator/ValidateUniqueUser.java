package com.updatestock.updatestock.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.updatestock.updatestock.model.User;
import com.updatestock.updatestock.repository.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Constraint(validatedBy = ValidateUniqueUser.UniqueUserValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUniqueUser {
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String message() default "";
    String fieldId();
    String fieldLogin();
    String fieldEmail();

    public class UniqueUserValidator implements ConstraintValidator<ValidateUniqueUser, Object> {
        private static final String MESSAGE_BAD_REQUEST_EMAIL = "Já existe um usuário com este E-mail.";
        private static final String MESSAGE_BAD_REQUEST_LOGIN = "Já existe um usuário com este Login.";

        private String fieldId;
        private String fieldEmail;
        private String fieldLogin;

        @Autowired
        private UserRepository userRepository;

        public void initialize(ValidateUniqueUser constraintAnnotation) {
            this.fieldId = constraintAnnotation.fieldId();
            this.fieldLogin = constraintAnnotation.fieldLogin();
            this.fieldEmail = constraintAnnotation.fieldEmail();
        }

        public boolean isValid(Object value, ConstraintValidatorContext context) {
            Integer id = (Integer) new BeanWrapperImpl(value).getPropertyValue(fieldId);
            String login = (String) new BeanWrapperImpl(value).getPropertyValue(fieldLogin);
            String email = (String) new BeanWrapperImpl(value).getPropertyValue(fieldEmail);

            //desativando messagem existente
            context.disableDefaultConstraintViolation();

            if (id != null) {
                Optional<User> optionalUser = this.userRepository.findById(id);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // verificando se o e-mail da base de dados é diferente do que está sendo
                    // passado pelo usuário
                    if (!user.getEmail().equalsIgnoreCase(email)) {
                        if (this.userRepository.findByEmail(email).isPresent()) {
                            context.buildConstraintViolationWithTemplate(MESSAGE_BAD_REQUEST_EMAIL)
                                    .addPropertyNode(fieldEmail)
                                    .addConstraintViolation();
                                return false;
                        }
                    }

                    // verificando se o login da base de dados é diferente do que está sendo
                    // passado pelo usuário
                    if (!user.getLogin().equalsIgnoreCase(login)) {
                        if (this.userRepository.findByLogin(login).isPresent()) {
                            context.buildConstraintViolationWithTemplate(MESSAGE_BAD_REQUEST_LOGIN)
                                    .addPropertyNode(fieldLogin)
                                    .addConstraintViolation();
                            return false;
                        }
                    }

                }
            } else { // verificando caso o id do usuário seja nulo
                // verificando se há algum email igual na base de dados
                if (this.userRepository.findByEmail(email).isPresent()) {
                    context.buildConstraintViolationWithTemplate(MESSAGE_BAD_REQUEST_EMAIL)
                            .addPropertyNode(fieldEmail)
                            .addConstraintViolation();
                    return false;
                }

                // verificando se há algum login igual na base de dados
                if (this.userRepository.findByLogin(login).isPresent()) {
                    context.buildConstraintViolationWithTemplate(MESSAGE_BAD_REQUEST_LOGIN)
                           .addPropertyNode(fieldLogin)
                           .addConstraintViolation();
                    return false;
                }
            }

            return true;
        }
    }

}
