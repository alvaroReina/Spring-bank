package com.alvaro.bank.exception;


public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String name) {
        super("Not found account with name: " + name);
    }
}
