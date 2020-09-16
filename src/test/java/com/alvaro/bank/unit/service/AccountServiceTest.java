package com.alvaro.bank.unit.service;

import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.exception.AccountCreationException;
import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.repository.AccountRepository;
import com.alvaro.bank.service.AccountService;
import com.alvaro.bank.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    public void initTest() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void getAllAccountsTest() {
        List<Account> accounts = new ArrayList<>();
        Account acc1 = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        Account acc2 = new Account(1L, "User2", Currency.USD, BigDecimal.TEN, false);
        accounts.add(acc1);
        accounts.add(acc2);

        when(accountRepository.findAll()).thenReturn(accounts);
        List<AccountDTO> actual = accountService.getAllAccounts();

        assertThat(actual).containsExactly(Utils.convertToDTO(acc1), Utils.convertToDTO(acc2));
    }

    @Test
    public void findExistingNameTest() {
        Account acc = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        when(accountRepository.findAccountByName("User1")).thenReturn(Optional.of(acc));

        AccountDTO actual = accountService.findAccountByName("User1");
        AccountDTO expected = Utils.convertToDTO(acc);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findNotExistingNameTest() {
        when(accountRepository.findAccountByName(any())).thenReturn(Optional.empty());

        String userToFind = "User";
        Throwable thrown = catchThrowableOfType(() -> accountService.findAccountByName(userToFind), AccountNotFoundException.class);
        assertThat(thrown).hasMessageContaining(userToFind);
    }

    @Test
    public void createAccountTest() {
        Account acc = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        when(accountRepository.existsAccountByName(any())).thenReturn(false);

        assertThatCode(() -> accountService.createAccount(acc)).doesNotThrowAnyException();
    }

    @Test
    public void createAccountWithInvalidBalanceTest() {
        Account acc = new Account(1L, "User1", Currency.USD, BigDecimal.TEN.negate(), false);

        Throwable thrown = catchThrowableOfType(() -> accountService.createAccount(acc), AccountCreationException.class);
        assertThat(thrown).hasMessageContaining(acc.getBalance().toString());
    }

    @Test
    public void createAccountWithRepeatedNameTest() {
        Account acc = new Account(1L, "User1", Currency.USD, BigDecimal.TEN, false);
        when(accountRepository.existsAccountByName(any())).thenReturn(true);

        Throwable thrown = catchThrowableOfType(() -> accountService.createAccount(acc), AccountCreationException.class);
        assertThat(thrown).hasMessageContaining(acc.getName());
    }
}
