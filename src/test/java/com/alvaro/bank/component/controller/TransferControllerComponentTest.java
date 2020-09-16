package com.alvaro.bank.unit.controller;

import com.alvaro.bank.controller.TransferController;
import com.alvaro.bank.model.Currency;
import com.alvaro.bank.model.Transfer;
import com.alvaro.bank.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests input validation and service calls.
 */
@WebMvcTest(TransferController.class)
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferService service;

    @Test
    public void whenTransferIsCalled() throws Exception {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.TEN, Currency.USD);
        mockMvc.perform(post("/api/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(transfer)))
                .andExpect(status().isOk());

        verify(service, times(1)).doTransfer(any());
    }

    //Same account transfer is tested on TransferService
    @Test
    public void shouldReturn404_onZeroAmountTransfer() throws Exception {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.ZERO, Currency.USD);
        mockMvc.perform(post("/api/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(transfer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn404_onNegativeAmountTransfer() throws Exception {
        Transfer transfer = new Transfer("User1", "User2", BigDecimal.ONE.negate(), Currency.USD);
        mockMvc.perform(post("/api/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(transfer)))
                .andExpect(status().isBadRequest());
    }
}
