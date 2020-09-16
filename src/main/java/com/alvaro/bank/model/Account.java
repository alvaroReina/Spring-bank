package com.alvaro.bank.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents an User Account in the system, uses the UUID type to identify an unique Account
 * to avoid potential vulnerabilities due to having sequential ids.
 * <p>
 * The Account Balance uses BigDecimal to represent exactly the amount. External libraries such as Joda-Money
 * were considered and they doesn't provide an actual improvement for this use case.
 */
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Size(min = 3, max = 30, message = "Account name must be between 3 and 30 characters")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    private BigDecimal balance;

    @Column(updatable = false)
    @NotNull
    private Boolean treasury = false;
}
