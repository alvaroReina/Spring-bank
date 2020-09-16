package com.alvaro.bank.validator;


import com.alvaro.bank.model.Account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * An account balance is only valid when the amount is positive or it is a treasury account.
 */
public class ValidBalanceValidator implements ConstraintValidator<ValidBalance, Account> {
    @Override
    public void initialize(ValidBalance constraintAnnotation) {

    }

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
        return account.getBalance().compareTo(BigDecimal.ZERO) >= 0 || account.getTreasury();
    }
}
