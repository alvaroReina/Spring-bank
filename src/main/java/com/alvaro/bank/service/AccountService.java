package com.alvaro.bank.service;

import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.NegativeBalanceException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account createAccount(Account newAccount) {
        newAccount.setId(null);
        if (newAccount.getBalance().compareTo(BigDecimal.ZERO) >= 0 || newAccount.getTreasury()) {
            return accountRepository.save(newAccount);
        } else {
            throw new NegativeBalanceException(newAccount.getBalance());
        }
    }

}
