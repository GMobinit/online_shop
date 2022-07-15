package com.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String referenceCode;
    private String result;
    private Currency currency;
    private BigDecimal amount;
    @ManyToOne(targetEntity = Transaction.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
