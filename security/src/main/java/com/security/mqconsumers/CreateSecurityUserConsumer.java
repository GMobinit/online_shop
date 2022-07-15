package com.security.mqconsumers;

import com.onlineshop.app.clients.security.CreateUserSecurityRequest;
import com.security.service.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
@AllArgsConstructor
public class CreateSecurityUserConsumer {

    private final UserSecurityService userSecurityService;

    @RabbitListener(queues = "${rabbitmq.queues.user.security}")
    public void createCloneUSerConsumer(CreateUserSecurityRequest userSecurityRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userSecurityService.createCloneUser(userSecurityRequest);
    }
}
