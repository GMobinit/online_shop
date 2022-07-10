package com.security.password.controller;

import com.security.password.model.PasswordResponse;
import com.security.password.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/security")
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping(path = "/salt")
    public PasswordResponse generateEncryptedPassword() throws Exception {
        String salt = passwordService.generateSalt();
        return new PasswordResponse(salt);
    }
    @GetMapping(path = "/encrypt_password/{salt}/{passWord}")
    public PasswordResponse generateEncryptedPassword(@PathVariable("salt") String salt ,@PathVariable("passWord") String password) throws Exception {
        String encryptedPassword = passwordService.generateEncryptedPassword(password,salt);
        return new PasswordResponse(encryptedPassword);
    }
}
