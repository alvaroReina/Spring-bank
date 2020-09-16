package com.alvaro.bank.model;

import java.math.BigDecimal;

/**
 * Represents the supported currencies by this system
 * and includes a fixed conversion rate for each one of them.
 */
public enum Currency {
    EUR(new BigDecimal("1.19"), new BigDecimal("0.84")),
    USD(new BigDecimal("1.00"), new BigDecimal("1.00")),
    GBP(new BigDecimal("1.29"), new BigDecimal("0.77"));

    private final BigDecimal rate;
    private final BigDecimal inverseRate;

    Currency(BigDecimal rate, BigDecimal inverse) {
        this.rate = rate;
        this.inverseRate = inverse;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getInverseRate() {
        return inverseRate;
    }
}

