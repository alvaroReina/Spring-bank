package com.alvaro.bank.unit.service;

import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.TransferException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.repository.AccountRepository;
import com.alvaro.bank.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferServiceTest {
    private AccountRepository accountRepository;

    private TransferService transferService;

    @BeforeEach
    public void initTest() {
        accountRepository = mock(AccountRepository.class);
        transferService = new TransferService(accountRepository);
    }

    @Test
    public void HappyTransferTest() {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.TEN, Currency.USD);
        Account acc1 = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        Account acc2 = new Account(2L, "User2", Currency.USD, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc1));
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.of(acc2));

        transferService.doTransfer(transfer);
        assertThat(acc1.getBalance().doubleValue()).isEqualTo(BigDecimal.ZERO.doubleValue());
        assertThat(acc2.getBalance().doubleValue()).isEqualTo(BigDecimal.valueOf(20).doubleValue());
    }

    @Test
    public void MissingSenderTransferTest() {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.TEN, Currency.USD);
        Account acc2 = new Account(2L, "User2", Currency.USD, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.empty());
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.of(acc2));

        Throwable thrown = catchThrowableOfType(() -> transferService.doTransfer(transfer), AccountNotFoundException.class);
        assertThat(thrown).hasMessageContaining("User1");
        assertThat(acc2.getBalance().doubleValue()).isEqualTo(BigDecimal.TEN.doubleValue());
    }

    @Test
    public void MissingReceiverTransferTest() {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.TEN, Currency.USD);
        Account acc1 = new Account(2L, "User1", Currency.USD, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc1));
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.empty());

        Throwable thrown = catchThrowableOfType(() -> transferService.doTransfer(transfer), AccountNotFoundException.class);
        assertThat(thrown).hasMessageContaining("User2");
        assertThat(acc1.getBalance().doubleValue()).isEqualTo(BigDecimal.TEN.doubleValue());
    }
    
    @Test
    public void SameSenderReceiverTransferTest() {
        Transfer transfer = new Transfer("User1", "User1", BigDecimal.TEN, Currency.USD);

        Throwable thrown = catchThrowableOfType(() -> transferService.doTransfer(transfer), TransferException.class);
        assertThat(thrown).hasMessageContaining("same");
    }

    @Test
    public void TransferAmountBiggerThanAvailableTest() {
        Transfer transfer = new Transfer("User1", "User2", new BigDecimal("20"), Currency.USD);
        Account acc1 = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        Account acc2 = new Account(2L, "User2", Currency.USD, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc1));
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.of(acc2));

        Throwable thrown = catchThrowableOfType(() -> transferService.doTransfer(transfer), TransferException.class);
        assertThat(thrown).hasMessageContaining(transfer.getAmount().toString());
    }

    @Test
    public void TransferAmountBiggerThanAvailableAndTreasuryTest() {
        Transfer transfer = new Transfer("User1", "User2", new BigDecimal("20"), Currency.USD);
        Account acc1 = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, true);
        Account acc2 = new Account(2L, "User2", Currency.USD, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc1));
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.of(acc2));

        transferService.doTransfer(transfer);

        assertThat(acc1.getBalance().doubleValue()).isEqualTo(BigDecimal.TEN.negate().doubleValue());
    }

    @Test
    public void DifferentCurrencyTransferTest() {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.TEN, Currency.USD);
        Account acc1 = new Account(1L, "User1", Currency.EUR, BigDecimal.TEN, false);
        Account acc2 = new Account(2L, "User2", Currency.GBP, BigDecimal.TEN, false);

        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc1));
        when(accountRepository.findAccountByName("User2")).thenReturn(Optional.of(acc2));

        assertThatCode(() -> transferService.doTransfer(transfer)).doesNotThrowAnyException();

        BigDecimal expectedBalance1 = BigDecimal.TEN
                .multiply(Currency.EUR.getRate())
                .subtract(BigDecimal.TEN)
                .multiply(Currency.EUR.getInverseRate());

        BigDecimal expectedBalance2 = BigDecimal.TEN
                .multiply(Currency.GBP.getRate())
                .add(BigDecimal.TEN)
                .multiply(Currency.GBP.getInverseRate());

        assertThat(acc1.getBalance().doubleValue()).isEqualTo(expectedBalance1.doubleValue());
        assertThat(acc2.getBalance().doubleValue()).isEqualTo(expectedBalance2.doubleValue());
    }
}
