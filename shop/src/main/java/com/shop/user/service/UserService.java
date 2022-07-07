package com.shop.user.service;

import com.shop.user.model.Gender;
import com.shop.user.model.User;
import com.shop.user.model.UserRegistrationRequest;
import com.shop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordGeneratorService passwordGeneratorService;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean registerUser(@RequestBody @Valid User user) throws Exception {

        String password = user.getPassword();

        String salt = passwordGeneratorService.generateSalt();
        String encryptedPassword = passwordGeneratorService.generateEncryptedPassword(password,salt);

        user.setPassword(encryptedPassword);
        user.setSalt(salt);

        userRepository.insert(user);

        return true;
    }
}
