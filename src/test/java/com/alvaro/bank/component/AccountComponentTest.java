package com.alvaro.bank.component;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.exception.AccountCreationException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.service.AccountService;
import com.alvaro.bank.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AccountComponentTest {
    @Autowired
    private AccountService accountService;

    /**
     * Test that initially there are no users, the number of users increases only after each successful creation
     */
    @Test
    public void createUserAndFindItTest() {
        assertThat(accountService.getAllAccounts()).size().isEqualTo(0);

        Account newAccount1 = new Account(null, "User1", Currency.USD, BigDecimal.TEN, false);
        accountService.createAccount(newAccount1);
        assertThat(accountService.getAllAccounts()).size().isEqualTo(1);

        catchThrowableOfType(() -> accountService.createAccount(newAccount1), AccountCreationException.class);
        assertThat(accountService.getAllAccounts()).size().isEqualTo(1);

        Account newAccount2 = new Account(null, "User2", Currency.USD, BigDecimal.TEN, false);
        accountService.createAccount(newAccount2);
        assertThat(accountService.getAllAccounts()).size().isEqualTo(2);

        Account invalidAccount = new Account(null, "User3", Currency.USD, BigDecimal.TEN.negate(), false);
        catchThrowableOfType(() -> accountService.createAccount(invalidAccount), AccountCreationException.class);
        assertThat(accountService.getAllAccounts()).size().isEqualTo(2);

        AccountDTO accDTO = Utils.convertToDTO(newAccount1);
        AccountDTO user1 = accountService.findAccountByName("User1");

        assertThat(user1.getName()).isEqualTo(accDTO.getName());
        assertThat(user1.getBalance().doubleValue()).isEqualTo(accDTO.getBalance().doubleValue());
        assertThat(user1.getCurrency()).isEqualTo(accDTO.getCurrency());
        assertThat(user1.isTreasury()).isEqualTo(accDTO.isTreasury());
    }
}
