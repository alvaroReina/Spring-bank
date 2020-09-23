package com.alvaro.bank.util;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;

public class Utils {

    /**
     * Simple DTO mapper from {@link Account} to {@link AccountDTO}
     * @param account
     * @return a DTO
     */
    public static AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getName(),
                account.getCurrency().name(),
                account.getBalance(),
                account.getTreasury()
        );
    }


}
