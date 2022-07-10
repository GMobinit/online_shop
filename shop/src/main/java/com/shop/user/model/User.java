package com.shop.user.model;

import com.shop.user.validator.Password;
import com.shop.user.validator.Phone;
import com.shop.user.validator.UserName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Document
@AllArgsConstructor
@Builder
@Valid
public class User {
    @Id
    private String id;
    @NotBlank(message = "{validation.firstName.mandatory}")
    @NotEmpty(message = "{validation.firstName.mandatory}")
    @NotNull(message = "{validation.firstName.mandatory}")
    private String firstName;
    private String middleName;
    private String lastName;
    @Indexed(unique = true)
    @UserName()
    @Pattern(regexp = "^[A-Za-z0-9 ]+$",message = "{validation.userName.invalid.special.chars}" )
    private String userName;
    @NotBlank(message = "{validation.email.mandatory}")
    @Email(message = "{validation.email.invalid}")
    private String email;
    @NotBlank(message = "{validation.email.mandatory}")
    @Phone(message = "{validation.phoneNumber.invalid}")
    private String phone;
    private Gender gender;
    @Password
    private String password;
    private String salt;
    @CreatedDate
    private Instant createdAt;
    @CreatedDate
    @LastModifiedDate
    private Instant updatedAt;

    public User() {
        super();
        this.gender = Gender.MALE;
    }
}
