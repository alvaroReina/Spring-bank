package com.alvaro.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
