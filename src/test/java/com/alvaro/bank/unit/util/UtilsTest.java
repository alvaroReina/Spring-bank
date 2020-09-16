package com.alvaro.bank.unit.util;

import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.util.Utils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    public void NormalizeSameCurrencyBalance() {
        Account account = new Account(1L, "a", Currency.USD, BigDecimal.TEN, false);
        BigDecimal normalizedValue = Utils.getNormalizedAccountBalance(account);
        assertEquals(normalizedValue.doubleValue(), account.getBalance().doubleValue());
    }

    @Test
    public void NormalizeCurrencyBalance() {
        Account account = new Account(1L, "a", Currency.EUR, BigDecimal.TEN, false);
        BigDecimal actualValue = Utils.getNormalizedAccountBalance(account);
        BigDecimal expectedValue = account.getBalance().multiply(account.getCurrency().getRate());
        assertEquals(expectedValue.doubleValue(), actualValue.doubleValue());
    }
}
