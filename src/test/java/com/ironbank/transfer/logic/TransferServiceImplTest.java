package com.ironbank.transfer.logic;

import com.ironbank.transfer.db.DaoFactoryDb;
import com.ironbank.transfer.db.TransactionManagerDb;
import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.InvalidOperationException;
import com.ironbank.transfer.persistence.DaoFactory;
import com.ironbank.transfer.service.AccountService;
import com.ironbank.transfer.service.AccountValidator;
import com.ironbank.transfer.service.TransactionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransferServiceImplTest {
    private final DaoFactory daoFactory = new DaoFactoryDb();
    private final TransactionManager txManager = new TransactionManagerDb();
    private final AccountValidator accountValidator = new AccountValidatorImpl();
    private final TransferServiceImpl transferService = new TransferServiceImpl(daoFactory, accountValidator, txManager);
    private final AccountService accountService = new AccountServiceImpl(daoFactory, txManager);

    private Account richAccount;
    private Account poorAccount;
    private final BigDecimal TRANSFER_AMOUNT = new BigDecimal(1000);

    @Before
    public void setUp() {
        richAccount = new Account(UUID.randomUUID(), new BigDecimal("1000000.00"));
        poorAccount = new Account(UUID.randomUUID(), new BigDecimal("100.00"));
        accountService.save(richAccount);
        accountService.save(poorAccount);
    }

    @Test
    public void transferSuccessTest() {
        // PREPARE
        BigDecimal beforeRichAmount = richAccount.getAmount();
        BigDecimal beforePoorAmount = poorAccount.getAmount();
        // ACT
        transferService.transfer(richAccount.getId(), poorAccount.getId(), TRANSFER_AMOUNT);
        // ASSERT
        BigDecimal afterRichAmount = accountService.get(richAccount.getId()).getAmount();
        BigDecimal afterPoorAmount = accountService.get(poorAccount.getId()).getAmount();
        Assert.assertEquals(beforeRichAmount.subtract(TRANSFER_AMOUNT), afterRichAmount);
        Assert.assertEquals(beforePoorAmount.add(TRANSFER_AMOUNT), afterPoorAmount);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferFailTest() {
        transferService.transfer(poorAccount.getId(), richAccount.getId(), TRANSFER_AMOUNT);
    }

    @Test
    public void concurrentTransferTest() throws InterruptedException {
        // PREPARE
        int parallel = 10;
        BigDecimal beforeRichAmount = richAccount.getAmount();
        BigDecimal beforePoorAmount = poorAccount.getAmount();
        BigDecimal transferSum = TRANSFER_AMOUNT.multiply(BigDecimal.valueOf(parallel));

        Collection<Callable<Void>> concurrentTransfers = new LinkedList<>();
        for (int i = 0; i < parallel; i++) {
            concurrentTransfers.add(() -> {
                transferService.transfer(richAccount.getId(), poorAccount.getId(), TRANSFER_AMOUNT);
                return null;
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(parallel);
        // ACT
        executorService.invokeAll(concurrentTransfers);
        executorService.awaitTermination(1500, TimeUnit.MILLISECONDS);
        BigDecimal afterRichAmount = accountService.get(richAccount.getId()).getAmount();
        BigDecimal afterPoorAmount = accountService.get(poorAccount.getId()).getAmount();
        // ASSERT
        Assert.assertEquals(beforeRichAmount.subtract(transferSum), afterRichAmount);
        Assert.assertEquals(beforePoorAmount.add(transferSum), afterPoorAmount);
    }

}
