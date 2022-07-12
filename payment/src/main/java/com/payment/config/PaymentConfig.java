package com.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Value("internal.exchange")
    private String internalExchange;

    @Value("${rabbitmq.queues.payment}")
    private String paymentQueue;

    @Value("internal.payment.routing-key")
    private String internalPaymentRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue paymentQueue(){
        return new Queue(paymentQueue);
    }

    @Bean
    public Binding internalToPaymentBinding(){
        return BindingBuilder.bind(paymentQueue()).to(internalTopicExchange()).with(internalPaymentRoutingKey);
    }

    public String getInternalExchange(){
        return internalExchange;
    }

    public String getPaymentQueue(){
        return paymentQueue;
    }

    public String getInternalPaymentRoutingKey(){
        return internalPaymentRoutingKey;
    }
}
