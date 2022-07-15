package com.security.model;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserModel(String firstName,
        String middleName,
        String lastName,
        String userName,
        String email,
        String phone,
        String gender,
        String password) {
}
