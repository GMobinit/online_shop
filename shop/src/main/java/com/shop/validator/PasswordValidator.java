package com.shop.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < 10){
            return false;
        }
        Pattern regex = Pattern.compile("[$&+,:;=?@#|'<>.^*()%!-]");
        Matcher matcher = regex.matcher(s);

        if (!(matcher.find())){
            return false;
        }
        return true;
    }
}
