package com.ironbank.transfer.db;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.PersistException;
import com.ironbank.transfer.persistence.AccountDao;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import java.util.UUID;

public class AccountDaoDb implements AccountDao {

    private final Session session;
    private final static LockOptions PESSIMISTIC_WRITE_LOCK = new LockOptions(LockMode.PESSIMISTIC_WRITE);

    public AccountDaoDb(Session session) {
        this.session = session;

    }

    @Override
    public Account loadForUpdate(UUID accountId) throws PersistException {
        try {
            return (Account) session.get(Account.class, accountId, PESSIMISTIC_WRITE_LOCK);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void persist(Account account) throws PersistException {
        try {
            if (account.getId() == null) {
                account.setId(UUID.randomUUID());
            }
            session.saveOrUpdate(account);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Account get(UUID accountId) throws PersistException {
        try {
            return (Account) session.get(Account.class, accountId);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
