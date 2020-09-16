package com.alvaro.bank.component;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.service.AccountService;
import com.alvaro.bank.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TransferComponentTesting {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    @Test
    public void TransferTest() {
        Account newAccount1 = new Account(null, "User1", Currency.USD, BigDecimal.TEN, true);
        Account newAccount2 = new Account(null, "User2", Currency.USD, BigDecimal.TEN, true);

        accountService.createAccount(newAccount1);
        accountService.createAccount(newAccount2);

        Transfer transfer = new Transfer("User1", "User2", new BigDecimal("20"), Currency.USD);
        transferService.doTransfer(transfer);

        AccountDTO acc1 = accountService.findAccountByName("User1");
        AccountDTO acc2 = accountService.findAccountByName("User2");

        assertThat(acc1.getBalance().doubleValue()).isEqualTo(BigDecimal.TEN.negate().doubleValue());
        assertThat(acc2.getBalance().doubleValue()).isEqualTo(new BigDecimal("30").doubleValue());
    }
}
