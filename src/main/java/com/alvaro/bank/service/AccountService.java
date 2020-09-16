package com.alvaro.bank.service;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.AccountCreationException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.repository.AccountRepository;
import com.alvaro.bank.util.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(Utils::convertToDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO findAccountByName(String name) {
        return accountRepository.findAccountByName(name).map(Utils::convertToDTO).orElseThrow(() -> new AccountNotFoundException(name));
    }

    public void createAccount(Account newAccount) {
        //Invalidate any id to force a new insertion.
        newAccount.setId(null);
        if (newAccount.checkBalance()) {
            if (accountRepository.existsAccountByName(newAccount.getName())) {
                throw new AccountCreationException("Name is already in use: " + newAccount.getName());
            }
            accountRepository.saveAndFlush(newAccount);
        } else {
            throw new AccountCreationException(newAccount.getBalance());
        }
    }


}
