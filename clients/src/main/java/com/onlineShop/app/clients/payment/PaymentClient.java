package com.onlineShop.app.clients.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
        name = "payment",
        url = "${clients.payment.url}"
)
@RequestMapping("api/v1")
public interface PaymentClient {
    @PostMapping("/create-user")
    void createUser(CreateUserRequest createUserReq);
}
