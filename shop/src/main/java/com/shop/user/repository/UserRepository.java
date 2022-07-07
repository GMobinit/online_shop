package com.shop.user.repository;

import com.shop.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUserName(String userName);
}
