package com.alvaro.bank.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class Transfer {
    @NotNull
    private String sender;
    @NotNull
    private String receiver;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    private Currency currency;
}
