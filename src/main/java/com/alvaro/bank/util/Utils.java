package com.alvaro.bank.util;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;

import java.math.BigDecimal;

public class Utils {

    /**
     * Simple DTO mapper from {@link Account} to {@link AccountDTO}
     * @param account
     * @return a DTO
     */
    public static AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(account.getName());
        accountDTO.setCurrency(account.getCurrency().name());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setTreasury(account.getTreasury());
        return accountDTO;
    }

    /**
     * Converts the balance of an account in any {@link Currency} to its value in USD
     *
     * @param account
     * @return
     */
    public static BigDecimal getNormalizedAccountBalance(Account account) {
        return account.getBalance().multiply(account.getCurrency().getRate());
    }

}
