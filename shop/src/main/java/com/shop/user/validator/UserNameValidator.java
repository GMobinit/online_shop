package com.shop.user.validator;

import com.shop.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UserNameValidator implements ConstraintValidator<UserName, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean exist = userRepository.findUserByUserName(s).isPresent();
        return !exist;
    }
}
