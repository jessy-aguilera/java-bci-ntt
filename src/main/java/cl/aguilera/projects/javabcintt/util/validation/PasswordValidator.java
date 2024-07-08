package cl.aguilera.projects.javabcintt.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^(?=(?:[^A-Z]*[A-Z]){1})(?=(?:\\D*\\d){2})[a-zA-Z0-9]{8,12}$");
        return pattern.matcher(value).matches();
    }
}
