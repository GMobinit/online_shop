package com.shop.user.controller;

import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.shop.user.service.UserService;
import com.shop.user.model.User;
import com.shop.user.model.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/fetch_all")
    public List<User> fetchAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/register")
    ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            checkValidations(bindingResult);
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {
                response.put("status", "success");
                response.put("message", "User created");
                status = HttpStatus.CREATED;
            } else {
                response.put("status", "failed");
                response.put("message", "Registration Failed");
            }
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response.toString(), status);
    }

    @PutMapping("update/{userId}")
    ResponseEntity<String> updateUser(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable String userId) {
        Map<String, String> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            checkValidations(bindingResult);
            boolean isUpdated = userService.updateUser(user, userId);
            if (isUpdated) {
                response.put("status", "success");
                response.put("message", "User updated");
                status = HttpStatus.CREATED;
            } else {
                response.put("status", "failed");
                response.put("message", "Update Failed");
            }
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response.toString(), status);
    }

    private void checkValidations(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = (bindingResult.getAllErrors());
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessages.append(error.getDefaultMessage()).append(", ");
            }
            throw new ValidationException(errorMessages.toString());
        }
    }
}
