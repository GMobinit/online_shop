package com.payment.mqconsumers;

import com.onlineShop.app.clients.payment.CreateUserRequest;
import com.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class createUserConsumer {
    private final PaymentService paymentService;

    @RabbitListener(queues = "${rabbitmq.queues.payment}")
    public void consumer(CreateUserRequest createUserRequest){
        log.info("Consumed {} from queue",createUserRequest);
        paymentService.createCloneUser(createUserRequest);
    }
}
