package com.alvaro.bank.exception;

import com.alvaro.bank.model.Currency;

import java.math.BigDecimal;

public class TransferException extends RuntimeException {
    public TransferException(String msg) {
        super(msg);
    }

    public TransferException(BigDecimal amount, Currency currency) {
        super("Sender account does not have enough balance to transfer: " + amount + " " + currency.name());
    }
}
