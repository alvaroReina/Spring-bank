package com.alvaro.bank.controller;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.service.AccountService;
import com.alvaro.bank.util.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts()
                .stream()
                .map(AccountUtils::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/accounts")
    public Account newAccount(@RequestBody Account newAccount) {
        return accountService.createAccount(newAccount);
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO findAccount(@PathVariable UUID id) {
        Account account = accountService.getAccount(id);
        return AccountUtils.convertToDTO(account);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }
}
