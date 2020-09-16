package com.alvaro.bank.component;

import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CreateUserTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void createUserTest() {
        assertThat(accountService.getAllAccounts()).size().isEqualTo(0);

        Account newAccount = new Account(null, "User1", Currency.USD, BigDecimal.TEN, false);
        accountService.createAccount(newAccount);
        assertThat(accountService.getAllAccounts()).size().isEqualTo(1);
    }
}
