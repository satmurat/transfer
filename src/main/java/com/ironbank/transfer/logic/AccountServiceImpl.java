package com.ironbank.transfer.logic;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.persistence.AccountDao;
import com.ironbank.transfer.persistence.DaoFactory;
import com.ironbank.transfer.service.AccountService;
import com.ironbank.transfer.service.TransactionManager;

import javax.inject.Inject;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private DaoFactory daoFactory;
    private TransactionManager txManager;

    @Inject
    public AccountServiceImpl(DaoFactory daoFactory, TransactionManager txManager) {
        this.daoFactory = daoFactory;
        this.txManager = txManager;
    }

    @Override
    public Account get(UUID id) {
        return (Account) txManager.doInTransaction(() -> {
            AccountDao accountDao = daoFactory.getAccountDao(txManager.getContext());
            return accountDao.get(id);
        });
    }

    @Override
    public Account save(Account account) {
        return (Account) txManager.doInTransaction(() -> {
            AccountDao accountDao = daoFactory.getAccountDao(txManager.getContext());
            accountDao.persist(account);
            return account;
        });
    }
}
