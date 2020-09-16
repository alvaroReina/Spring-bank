package com.alvaro.bank.service;

import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    @Autowired
    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void doTransfer(Transfer transfer) {

    }
}
