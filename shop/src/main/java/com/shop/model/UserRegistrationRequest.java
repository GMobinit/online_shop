package com.shop.model;

public record UserRegistrationRequest (
        String firstName,
        String middleName,
        String lastName,
        String userName,
        String email,
        String phone,
        Gender gender){
}
