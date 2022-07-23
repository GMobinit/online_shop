package com.shop.validator;

        import com.shop.model.PhoneNumber;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.params.ParameterizedTest;
        import org.junit.jupiter.params.provider.CsvSource;

        import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PhoneNumberValidatorTest {
    private PhoneNumberValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PhoneNumberValidator();
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "+4930901820,DE,true",
                    "+49 30 '01820,DE,false",
                    "049 30 901820,DE,false",
                    "+4915223433333,DE,true",
                    "+0172196522.4,GB,false",
                    "447975777666,GB,true"
            }
    )
    void itShouldValidatePhoneNumber(String phoneNumber,String region, boolean validationExpectation ) {
        PhoneNumber phoneNumberObject = new PhoneNumber(phoneNumber,region);
        boolean isValid = underTest.isValid(phoneNumberObject,null);

        assertThat(isValid).isEqualTo(validationExpectation);

    }

    @ParameterizedTest
    @CsvSource(
            {
                    "+4930901820,CH,false",
                    "+49 30 01820,AF,false",
            }
    )
    void itShouldThrowException(String phoneNumber,String region,boolean validationExpectation){
        PhoneNumber phoneNumberObject = new PhoneNumber(phoneNumber,region);
        boolean isValid = underTest.isValid(phoneNumberObject,null);

        assertThat(isValid).isEqualTo(validationExpectation);
    }
}