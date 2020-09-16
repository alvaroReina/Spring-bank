package com.alvaro.bank.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Not found account " + id);
    }
}
