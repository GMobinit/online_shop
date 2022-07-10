package com.shop.user.model;

import com.shop.user.validator.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRegistrationRequest (
        String firstName,
        String middleName,
        String lastName,
        String userName,
        String email,
        String phone,
        Gender gender){
}
