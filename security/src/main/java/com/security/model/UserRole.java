package com.security.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserRole {
    @Id
    private String id;
    private String name;
}
