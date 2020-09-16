package com.alvaro.bank.controller;

import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/accounts")
    public Account newAccount(@RequestBody Account newAccount) {
        return accountService.createAccount(newAccount);
    }

    @GetMapping("/accounts/{id}")
    public Account findAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }
}
