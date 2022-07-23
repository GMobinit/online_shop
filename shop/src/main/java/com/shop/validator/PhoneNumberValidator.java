package com.shop.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.shop.model.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PhoneNumberValidator implements ConstraintValidator<Phone,PhoneNumber> {
    @Override
    public boolean isValid(PhoneNumber phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber numberProto;
        try {
            numberProto = phoneNumberUtil.parse(phoneNumber.phoneNumber(), phoneNumber.region());
            if (!Objects.equals(phoneNumber.region(), "DE") &&
                    !Objects.equals(phoneNumber.region(), "GB") &&
                    !Objects.equals(phoneNumber.region(), "US")){
                throw new IllegalStateException("Region not supported");
            }
        } catch (Exception e) {
            return false;
        }
        System.out.println(phoneNumber + " " + phoneNumberUtil.getNumberType(numberProto));
        return phoneNumberUtil.isValidNumber(numberProto);
    }
}
