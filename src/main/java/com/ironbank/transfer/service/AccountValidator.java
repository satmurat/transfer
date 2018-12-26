package com.ironbank.transfer.service;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.InvalidOperationException;

import java.math.BigDecimal;

public interface AccountValidator {
    void validateAvailability(Account from, BigDecimal amount) throws InvalidOperationException;
}
