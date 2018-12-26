package com.ironbank.transfer.logic;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.InvalidOperationException;
import com.ironbank.transfer.service.AccountValidator;

import java.math.BigDecimal;

public class AccountValidatorImpl implements AccountValidator {

    @Override
    public void validateAvailability(Account from, BigDecimal amount) throws InvalidOperationException {
        if (from.getAmount().compareTo(amount) < 0) {
            throw new InvalidOperationException("Account has amount less than " + amount);
        }
    }
}
