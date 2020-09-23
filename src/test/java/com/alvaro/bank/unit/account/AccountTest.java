package com.alvaro.bank.unit.account;


import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Account methods.
 */
public class AccountTest {

    @Test
    public void PositiveBalanceTest(){
        Account account = new Account(1L, "a", Currency.USD, BigDecimal.TEN, false);
        assertTrue(account.isValidBalance());
    }

    @Test
    public void ZeroBalanceTest() {
        Account account = new Account(1L, "a", Currency.USD, BigDecimal.ZERO, false);
        assertTrue(account.isValidBalance());
    }

    @Test
    public void NegativeBalanceTest() {
        Account account = new Account(1L, "a", Currency.USD, BigDecimal.TEN.negate(), false);
        assertFalse(account.isValidBalance());
    }

    @Test
    public void TreasuryNegativeBalanceTest() {
        Account account = new Account(1L, "a", Currency.USD, BigDecimal.TEN.negate(), true);
        assertTrue(account.isValidBalance());
    }
}
