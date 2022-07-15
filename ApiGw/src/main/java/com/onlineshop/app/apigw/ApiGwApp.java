package com.onlineshop.app.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGwApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGwApp.class,args);
    }
}
