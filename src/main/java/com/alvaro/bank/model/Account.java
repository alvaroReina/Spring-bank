package com.alvaro.bank.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String currency;
    private BigDecimal balance;
    @Column(updatable = false)
    private Boolean treasury;
}
