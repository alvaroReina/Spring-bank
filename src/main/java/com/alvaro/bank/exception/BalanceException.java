package com.alvaro.bank.exception;

public class BalanceException extends RuntimeException{
    public BalanceException(String name) {
        super("Invalid balance for account " + name);
    }
}
