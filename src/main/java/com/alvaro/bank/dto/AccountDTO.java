package com.alvaro.bank.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private String name;
    private String currency;
    private BigDecimal balance;
    private boolean treasury;
}
