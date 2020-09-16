package com.alvaro.bank.model;

import com.alvaro.bank.validator.ValidBalance;
import lombok.Data;

import javax.persistence.*;
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
@ValidBalance
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 3, max = 30, message = "Account name must be between 3 and 30 characters")
    private String name;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;

    @Column(updatable = false)
    private Boolean treasury;
}
