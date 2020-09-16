package com.alvaro.bank.model;

import java.math.BigDecimal;

/**
 * Represents the supported currencies by this system
 * and includes a fixed conversion rate for each one of them.
 */
public enum Currency {
    EUR(new BigDecimal("1.19")),
    USD(new BigDecimal("1.00")),
    GBP(new BigDecimal("1.28"));

    private final BigDecimal rate;

    Currency(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }
}

