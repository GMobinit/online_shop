package com.shop.user.model;

import com.shop.user.validator.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRegistrationRequest (
//        @NotBlank
        String firstName,
        String middleName,
        String lastName,
        String userName,
//        @Email(message = "Email is invalid")
        String email,
//        @Phone(message = "Phone number is invalid")
        String phone,
        Gender gender){
}
