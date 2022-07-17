package com.payment.model;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String systemUserId;
    private Long wallet;
    @DecimalMin(value = "0.0",message = "Balance can not be less than 0")
    private BigDecimal balance;
    private Currency currency;
    @OneToMany(targetEntity = Transaction.class, mappedBy = "user")
    private List<Transaction> transaction = new ArrayList<>();
}
