package com.shop.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "{validation.password.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default  { };
}