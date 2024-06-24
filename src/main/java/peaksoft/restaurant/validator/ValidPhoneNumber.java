package peaksoft.restaurant.validator;

import jakarta.validation.Constraint;

import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})

public @interface ValidPhoneNumber {

    String message() default "Phone number should start with +996 and consist of 13 digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
