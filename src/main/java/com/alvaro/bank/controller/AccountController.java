package com.alvaro.bank.controller;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = "application/json")
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping(value = "/accounts/search", produces = "application/json")
    public AccountDTO searchAccounts(@RequestParam(value = "name") String name) {
        return accountService.findAccountByName(name);
    }

    @PostMapping(value = "/accounts", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void newAccount(@RequestBody @Valid Account newAccount) {
        accountService.createAccount(newAccount);
    }
}
