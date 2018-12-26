package com.ironbank.transfer.logic;

import com.ironbank.transfer.db.DaoFactoryDb;
import com.ironbank.transfer.db.TransactionManagerDb;
import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.persistence.DaoFactory;
import com.ironbank.transfer.service.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountServiceImplTest {
    private final DaoFactory daoFactory = new DaoFactoryDb();
    private final TransactionManager txManager = new TransactionManagerDb();
    private final AccountServiceImpl service = new AccountServiceImpl(daoFactory, txManager);

    @Test
    public void saveTest() {
        // PREPARE
        Account account = new Account();
        account.setAmount(new BigDecimal(100));
        // ACT
        Account savedAccount = service.save(account);
        // ASSERT
        Assert.assertNotNull(savedAccount.getId());
        Assert.assertEquals(account.getAmount(), savedAccount.getAmount());
    }

    @Test
    public void getExistTest() {
        // PREPARE
        Account account = new Account();
        account.setAmount(new BigDecimal(100));
        // ACT
        service.save(account);
        Account accountGet = service.get(account.getId());
        // ASSERT
        Assert.assertNotNull(accountGet);
    }

    @Test
    public void getNotExistTest() {
        // ACT
        Account account = service.get(UUID.randomUUID());
        // ASSERT
        Assert.assertNull(account);
    }

}
