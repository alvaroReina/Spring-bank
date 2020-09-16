package com.alvaro.bank.exception;

import java.math.BigDecimal;

public class AccountCreationException extends RuntimeException{
    public AccountCreationException(String msg) {
        super(msg);
    }

    public AccountCreationException(BigDecimal balance) {
        super("Invalid balance in non treasury account: " + balance);
    }
}
