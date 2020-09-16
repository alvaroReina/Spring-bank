package com.alvaro.bank.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiErrorDTO {
    private Instant timestamp;
    private int status;
    private String message;

    public ApiErrorDTO(int status, String message) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
    }
}
