package com.shop.model;

import com.shop.model.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@Data
@Document
@Builder
public class Product {
    @Id
    private String id;
    private Category category;
    private String name;
    private String description;
    private BigDecimal price;
    private Currency currency;
    @CreatedDate
    private Instant createdAt;
    @CreatedDate
    @LastModifiedDate
    private Instant updatedAt;
}
