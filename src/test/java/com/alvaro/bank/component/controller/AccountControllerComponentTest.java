package com.alvaro.bank.unit.controller;

import com.alvaro.bank.controller.AccountController;
import com.alvaro.bank.dto.AccountDTO;
import com.alvaro.bank.model.Account;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests input validation and service calls.
 */
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService service;

    @Test
    public void whenGetAllAccountsIsCalled() throws Exception {
        List<AccountDTO> accounts = new ArrayList<>();
        AccountDTO account = new AccountDTO("user", "USD", new BigDecimal("10.20"), false);
        accounts.add(account);
        when(service.getAllAccounts()).thenReturn(accounts);
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(account.getName())))
                .andExpect(jsonPath("$[0].currency", is(account.getCurrency())))
                .andExpect(jsonPath("$[0].balance", is(account.getBalance().doubleValue())))
                .andExpect(jsonPath("$[0].treasury", is(account.isTreasury())));
    }

    //In a real project, more combinations of wrong account parameters needs to be tested.
    @Test
    public void shouldReturn400_onInvalidNewAccount() throws Exception {
        Account account = new Account(null, null, null, null, null);
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andExpect(status().isBadRequest());
    }

    //Negative balance on non treasury account is tested on AccountService.
    @Test
    public void shouldCreateAccount() throws Exception {
        Account account = new Account(null, "user", Currency.USD, BigDecimal.TEN, false);
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400_onInvalidSearchQuery() throws Exception {
        mockMvc.perform(get("/api/accounts/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAccountWhenSearchIsCalled() throws Exception {
        AccountDTO account = new AccountDTO("user", "USD", new BigDecimal("10.20"), false);
        when(service.findAccountByName(anyString())).thenReturn(account);

        mockMvc.perform(get("/api/accounts/search?name=user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(account.getName())))
                .andExpect(jsonPath("$.currency", is(account.getCurrency())))
                .andExpect(jsonPath("$.balance", is(account.getBalance().doubleValue())))
                .andExpect(jsonPath("$.treasury", is(account.isTreasury())));
    }
}
