package com.security.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.user.security}")
    private String userSecurityQueue;

    @Value("${rabbitmq.routing-keys.internal-user-security}")
    private String internalUserSecurityRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue userSecurityQueue(){
        return new Queue(userSecurityQueue);
    }

    @Bean
    public Binding internalToUserSecurityBinding(){
        return BindingBuilder.bind(userSecurityQueue()).to(internalTopicExchange()).with(internalUserSecurityRoutingKey);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public String getInternalExchange(){
        return internalExchange;
    }

    public String getUserSecurityQueue(){
        return userSecurityQueue;
    }

    public String getInternalUserSecurityRoutingKey(){
        return internalUserSecurityRoutingKey;
    }
}
