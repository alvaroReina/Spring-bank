package com.alvaro.bank.model;

import com.alvaro.bank.exception.BalanceException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Represents an User Account in the system, uses a sequential Long ID generator since the ID
 * will never be exposed to the client. Otherwise i would have chosen a random generated UUID.
 *
 * <p>
 * The Account Balance uses BigDecimal to represent exactly the amount. External libraries such as Joda-Money
 * were considered and they doesn't provide an actual improvement for this use case.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "Account name must be between 3 and 30 characters")
    @Column(unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    private BigDecimal balance;

    @Column(updatable = false)
    @NotNull
    private Boolean treasury = false;

    @JsonIgnore
    public boolean isValidBalance() {
        return this.balance.compareTo(BigDecimal.ZERO) >= 0 || this.treasury;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0 && !this.treasury) {
            throw new BalanceException(this.name);
        }
        this.balance = balance;
    }

    public void addBalance(BigDecimal amount, Currency currency) {
        BigDecimal newAmount = getNormalizedBalance().add(normalizeAmount(amount, currency));
        setBalance(newAmount.multiply(this.currency.getInverseRate()));
    }

    public void subtractBalance(BigDecimal amount, Currency currency) {
        BigDecimal newAmount = getNormalizedBalance().subtract(normalizeAmount(amount, currency));
        setBalance(newAmount.multiply(this.currency.getInverseRate()));
    }

    /**
     * Converts the balance of an account in any {@link Currency} to its value in USD
     *
     */
    private BigDecimal getNormalizedBalance() {
        return normalizeAmount(balance, currency);
    }

    private BigDecimal normalizeAmount(BigDecimal amount, Currency currency) {
        return amount.multiply(currency.getRate());
    }
}
