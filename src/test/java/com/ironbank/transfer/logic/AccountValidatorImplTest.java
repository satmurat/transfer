package com.ironbank.transfer.logic;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.InvalidOperationException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountValidatorImplTest {
    // PREPARE
    private AccountValidatorImpl accountValidator = new AccountValidatorImpl();
    private final BigDecimal TRANSFER_AMOUNT = new BigDecimal(1000);
    private Account richAccount = new Account(UUID.randomUUID(), new BigDecimal(1000000));
    private Account poorAccount = new Account(UUID.randomUUID(), new BigDecimal(100));

    @Test
    public void transferFromRichTest() throws InvalidOperationException {
        // ACT, ASSERT
        accountValidator.validateAvailability(richAccount, TRANSFER_AMOUNT);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferFromPoorTest() throws InvalidOperationException {
        // ACT, ASSERT
        accountValidator.validateAvailability(poorAccount, TRANSFER_AMOUNT);
    }
}
