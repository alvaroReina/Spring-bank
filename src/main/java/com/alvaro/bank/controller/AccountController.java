package com.alvaro.bank.controller;

import com.alvaro.bank.model.Account;
import com.alvaro.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @PostMapping("/accounts")
    public Account newAccount(@RequestBody Account newAccount) {
        return accountRepository.save(newAccount);
    }
}
