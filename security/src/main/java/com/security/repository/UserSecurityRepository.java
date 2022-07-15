package com.security.repository;

import com.security.model.UserSecurity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserSecurityRepository extends MongoRepository<UserSecurity,String> {
    Optional<UserSecurity> findUserSecurityByUserName(String userName);
}
