package com.alvaro.bank.exception;

import java.math.BigDecimal;

public class NegativeBalanceException extends RuntimeException{
    public NegativeBalanceException(BigDecimal balance) {
        super("Invalid balance in non treasury account: " + balance);
    }
}
