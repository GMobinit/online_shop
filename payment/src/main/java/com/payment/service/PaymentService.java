package com.payment.service;

import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.payment.model.User;
import com.payment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    public void createCloneUser(CreateUserRequest createUserRequest){
        Optional<User> existingUser = userRepository.findUserBySystemUserId(createUserRequest.userId());

        if (existingUser.isEmpty()){
            Long wallet = (long) (Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
            User newUser = User.builder().systemUserId(createUserRequest.userId()).wallet(wallet).balance(BigDecimal.valueOf(0)).currency(Currency.getInstance("EUR"))
                    .build();
            userRepository.saveAndFlush(newUser);
        }
    }
}
