package com.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.Instant;
import java.util.ArrayList;

import static javax.persistence.FetchType.*;

@Data
@Builder
@AllArgsConstructor
public class UserSecurity {
    @Id
    private String id;
    private String systemUserId;
    private String userName;
    private String password;
    private String salt;
    private String encryptedPassword;
    @ManyToMany(fetch = EAGER)
    private ArrayList<UserRole> roles = new ArrayList<>();
    @CreatedDate
    private Instant createdAt;
    @CreatedDate
    @LastModifiedDate
    private Instant updatedAt;
}
