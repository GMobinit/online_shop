package com.shop.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface Phone {
    String message() default "{validation.phoneNumber.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default  { };
}
