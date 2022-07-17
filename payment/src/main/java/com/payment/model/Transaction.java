package com.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Transaction {

    public Transaction(){
        this.referenceCode = UUID.randomUUID().toString();
    }

    public static final String TRANSACTION_RESULT_SUCCESS = "Success";
    public static final String TRANSACTION_RESULT_FAILED = "Failed";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String referenceCode;
    private String result;
    private Currency currency;
    private BigDecimal amount;
    private String message;
    private String productId;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
