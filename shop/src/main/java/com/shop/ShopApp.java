package com.shop;

import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.math.BigDecimal;
import java.util.Currency;

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

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            Product product1 = Product.builder().category(Category.CLOTHES).currency(Currency.getInstance("EUR"))
                    .description("Winter clothes").name("Cloth 1").price(new BigDecimal(20)).build();
            Product product2 = Product.builder().category(Category.DIGITAL_DEVICES).currency(Currency.getInstance("EUR"))
                    .description("New digital product").name("Digital 1").price(new BigDecimal(40)).build();
            Product product3 = Product.builder().category(Category.VIDEO_GAMES).currency(Currency.getInstance("EUR"))
                    .description("First video game").name("Video game 1").price(new BigDecimal(60)).build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        };
    }

}
