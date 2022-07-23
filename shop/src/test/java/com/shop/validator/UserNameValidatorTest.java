package com.shop.validator;

import com.shop.model.User;
import com.shop.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserNameValidatorTest {
    private UserNameValidator underTest;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new UserNameValidator(userRepository);
    }

    @Test
    void checkValidationWhenUserNameDoesNotExist() {
        String userName = "anything";

        given(userRepository.findUserByUserName(anyString())).willReturn(Optional.empty());
        boolean isValid = underTest.isValid(userName,null);

        assertThat(isValid).isEqualTo(true);
    }
    @Test
    void checkValidationWhenUserNameDoesExist() {
        String userName = "anything";

        Optional<User> user = Optional.of(new User());
        given(userRepository.findUserByUserName(anyString())).willReturn(user);

        boolean isValid = underTest.isValid(userName,null);

        assertThat(isValid).isEqualTo(false);
    }
}