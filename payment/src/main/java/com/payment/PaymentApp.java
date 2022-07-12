package com.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.payment",
                "com.onlineShop.app.amqp"
        }
)
@EnableEurekaClient
@PropertySources(
        @PropertySource({
                "classpath:clients-default.properties"
        })
)
@EnableFeignClients(
        basePackages = "com.onlineShop.app.clients"
)
public class PaymentApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApp.class,args);
    }
}
