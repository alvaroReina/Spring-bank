package com.alvaro.bank.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Transfer {
    @NotNull
    private UUID sender;
    @NotNull
    private UUID receiver;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    private Currency currency;
}
