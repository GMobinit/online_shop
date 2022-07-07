package com.shop.product.model;

import com.shop.product.model.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Document
public class Product {
    @Id
    private String id;
    private Category category;
    private String name;
    private String description;
    private BigDecimal price;
    private Currency currency;
}
