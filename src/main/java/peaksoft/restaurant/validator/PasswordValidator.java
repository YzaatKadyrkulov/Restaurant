package peaksoft.restaurant.validator;

import peaksoft.restaurant.entities.Restaurant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        // Add your password validation logic here
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()].*");
        boolean isAtLeast8 = password.length() >= 8;

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar && isAtLeast8;
    }
}