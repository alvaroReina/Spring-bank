package com.alvaro.bank.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Transfer {
    @NotNull
    private UUID from;
    @NotNull
    private UUID to;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
}
