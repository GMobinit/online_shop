package com.shop.user.service;

import com.onlineShop.app.amqp.RabbitMqMessageProducer;
import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.onlineshop.app.clients.security.CreateUserSecurityRequest;
import com.shop.user.model.Gender;
import com.shop.user.model.PasswordResponse;
import com.shop.user.model.User;
import com.shop.user.model.UserRegistrationRequest;
import com.shop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean registerUser(@RequestBody @Valid User user) throws Exception {

        userRepository.save(user);

        CreateUserRequest createUserRequest = new CreateUserRequest(user.getId());
        CreateUserSecurityRequest userSecurityRequest = new CreateUserSecurityRequest(user.getId(),user.getUserName(), user.getPassword());
        rabbitMqMessageProducer.publish(createUserRequest,"internal.exchange","internal.payment.routing-key");
        rabbitMqMessageProducer.publish(userSecurityRequest,"internal.exchange","internal.user.security.routing-key");

        return true;
    }

    public boolean updateUser(User user, String userId) {
        Optional<User> existUser = userRepository.findById(userId);

        if (existUser.isEmpty()){
            throw new IllegalStateException("This user doesn't exist and can't be updated");
        }
        User relatedUser = existUser.get();
        relatedUser.setGender(user.getGender());
        relatedUser.setPhone(user.getPhone());
        relatedUser.setEmail(user.getEmail());
        relatedUser.setLastName(user.getLastName());
        relatedUser.setMiddleName(user.getMiddleName());
        relatedUser.setFirstName(user.getFirstName());
        userRepository.save(relatedUser);
        return true;
    }
}
