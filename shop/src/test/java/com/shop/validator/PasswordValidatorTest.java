package com.shop.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordValidatorTest {
    private PasswordValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PasswordValidator();
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "123,false",
                    "hujakjasdu,false",
                    "huaksjdpow@,true",
                    "1234567891,false",
                    "123456789@,true",
                    "123456789@a,true"
            }
    )
    void itShouldValidatePassword(String password, boolean validationExpectation) {
        boolean isValid = underTest.isValid(password,null);

        assertThat(isValid).isEqualTo(validationExpectation);
    }
}