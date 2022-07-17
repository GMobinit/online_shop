package com.shop.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
@Documented
public @interface UserName {
    String message() default "{validation.userName.invalid.duplicate}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default  { };
}
