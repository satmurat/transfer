package com.ironbank.transfer.logic;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.persistence.AccountDao;
import com.ironbank.transfer.persistence.DaoFactory;
import com.ironbank.transfer.service.AccountValidator;
import com.ironbank.transfer.service.TransactionManager;
import com.ironbank.transfer.service.TransferService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.UUID;

public class TransferServiceImpl implements TransferService {
    private DaoFactory daoFactory;
    private AccountValidator accountValidator;
    private TransactionManager txManager;

    @Inject
    public TransferServiceImpl(DaoFactory daoFactory, AccountValidator accountValidator, TransactionManager txManager) {
        this.daoFactory = daoFactory;
        this.accountValidator = accountValidator;
        this.txManager = txManager;
    }

    @Override
    public void transfer(UUID fromId, UUID toId, BigDecimal amount) {
        txManager.doInTransaction(() -> {
            AccountDao accountDao = daoFactory.getAccountDao(txManager.getContext());
            Account from = accountDao.loadForUpdate(fromId);
            Account to = accountDao.loadForUpdate(toId);
            accountValidator.validateAvailability(from, amount);
            from.setAmount(from.getAmount().subtract(amount));
            to.setAmount(to.getAmount().add(amount));
            accountDao.persist(from);
            accountDao.persist(to);
            return null;
        });
    }
}
