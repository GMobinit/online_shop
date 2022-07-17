package com.shop.service;

import com.onlineShop.app.amqp.RabbitMqMessageProducer;
import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.shop.model.PasswordResponse;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
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

        String password = user.getPassword();

        PasswordResponse salt = restTemplate.getForObject("http://SECURITY/api/v1/security/salt", PasswordResponse.class);
        assert salt != null;
        HashMap<String,String> params = new HashMap<String,String>();
        String s = salt.saltOrPass();
        params.put("salt",s);
        params.put("passWord",user.getPassword());
        PasswordResponse encryptedPassword = restTemplate.getForObject("http://SECURITY/api/v1/security/encrypt_password/{salt}/{passWord}", PasswordResponse.class,params);

        assert encryptedPassword != null;
        user.setPassword(encryptedPassword.saltOrPass());
        user.setSalt(s);

        userRepository.save(user);

        CreateUserRequest createUserRequest = new CreateUserRequest(user.getId());
        rabbitMqMessageProducer.publish(createUserRequest,"internal.exchange","internal.payment.routing-key");

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
