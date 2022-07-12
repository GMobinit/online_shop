package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(
        scanBasePackages = {
                "com.shop",
                "com.onlineShop.app.amqp"
        }
)
@EnableEurekaClient
@EnableMongoAuditing
@EnableFeignClients(
        basePackages = "com.onlineShop.app.clients"
)
@PropertySources({
        @PropertySource("classpath:clients-default.properties")
})
public class ShopApp {
    public static void main(String[] args) {
        SpringApplication.run(ShopApp.class, args);
    }

}
