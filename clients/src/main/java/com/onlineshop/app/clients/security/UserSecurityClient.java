package com.onlineshop.app.clients.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
        name = "user_security",
        url = "${clients.security.url}"
)
@RequestMapping("api/v1")
public interface UserSecurityClient {
    @PostMapping("/create-user")
    void createUser(CreateUserSecurityRequest createUserReq);
}
