package com.alvaro.bank.model;

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

    /**
     * Checks if the balance is valid for the given account type.
     */
    public boolean checkBalance() {
        return this.getBalance().compareTo(BigDecimal.ZERO) >= 0 || this.getTreasury();
    }
}
