package com.ironbank.transfer.persistence;

import com.ironbank.transfer.db.AccountDaoDb;
import com.ironbank.transfer.db.DbModule;
import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.PersistException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDaoDbTest {

    private AccountDaoDb accountDaoDb;
    private Session session;


    @Before
    public void setUp() {
        session = newSession();
        accountDaoDb = new AccountDaoDb(session);
        session.beginTransaction();
    }

    @After
    public void tearDown() {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        if (session.isOpen()) {
            session.close();
        }
    }

    @Test
    public void testCreateAndGet() throws PersistException {
        // PREPARE
        Account account = buildAccount();
        // ACT
        accountDaoDb.persist(account);
        Account newAccount = accountDaoDb.get(account.getId());
        // ASSERT
        Assert.assertNotNull(newAccount);
    }

    @Test
    public void testConcurrentLoadForUpdate() throws PersistException {
        // PREPARE
        Account account = buildAccount();
        accountDaoDb.persist(account);

        session.getTransaction().commit();
        session.close();

        session = newSession();
        session.beginTransaction();
        accountDaoDb = new AccountDaoDb(session);

        Session anotherSession = newSession();
        AccountDaoDb anotherAccountDaoDb = new AccountDaoDb(anotherSession);
        boolean lockExceptionThrown = false;

        // ACT
        accountDaoDb.loadForUpdate(account.getId());
        try {
            anotherAccountDaoDb.loadForUpdate(account.getId());
        } catch (PersistException ignored) {
            lockExceptionThrown = true;
        }
        // ASSERT
        Assert.assertTrue(lockExceptionThrown);
    }

    private Account buildAccount() {
        return new Account(UUID.randomUUID(), BigDecimal.valueOf(1000.00d));
    }

    private Session newSession() {
        return DbModule.getSessionFactory().openSession();
    }

}
