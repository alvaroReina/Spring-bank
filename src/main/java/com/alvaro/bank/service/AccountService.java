package com.alvaro.bank.service;

import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account createAccount(Account newAccount) {
        newAccount.setId(-1);
        return accountRepository.save(newAccount);
    }

}
