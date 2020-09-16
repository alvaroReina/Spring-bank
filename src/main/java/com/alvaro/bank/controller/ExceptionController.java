package com.alvaro.bank.controller;

import com.alvaro.bank.dto.ApiErrorDTO;
import com.alvaro.bank.exception.AccountNotFoundException;
import com.alvaro.bank.exception.AccountCreationException;
import com.alvaro.bank.exception.TransferException;
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

    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<ApiErrorDTO> invalidBalanceHandler(AccountCreationException ex) {
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        ApiErrorDTO apiError = new ApiErrorDTO(responseStatus.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, null, responseStatus);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ApiErrorDTO> transferExceptionHandler(TransferException ex) {
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        ApiErrorDTO apiError = new ApiErrorDTO(responseStatus.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, null, responseStatus);
    }
}
