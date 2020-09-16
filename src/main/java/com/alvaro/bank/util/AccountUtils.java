package com.alvaro.bank.util;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;

public class AccountUtils {
    public static AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(account.getName());
        accountDTO.setCurrency(account.getCurrency().name());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setTreasury(account.getTreasury());
        return accountDTO;
    }
}
