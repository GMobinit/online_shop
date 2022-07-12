package com.payment.controller;

import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody CreateUserRequest createUserRequest){
        paymentService.createCloneUser(createUserRequest);
    }
}
