package com.alvaro.bank.controller;

import com.alvaro.bank.dto.ApiErrorDTO;
import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.BalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> accountNotFoundHandler(AccountNotFoundException ex) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        ApiErrorDTO apiError = new ApiErrorDTO(responseStatus.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, null, responseStatus);
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<ApiErrorDTO> invalidBalanceHandler(BalanceException ex) {
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        ApiErrorDTO apiError = new ApiErrorDTO(responseStatus.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, null, responseStatus);
    }
}
