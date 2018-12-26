package com.ironbank.transfer.application;

import com.ironbank.transfer.db.DaoFactoryDb;
import com.ironbank.transfer.db.TransactionManagerDb;
import com.ironbank.transfer.logic.AccountServiceImpl;
import com.ironbank.transfer.logic.AccountValidatorImpl;
import com.ironbank.transfer.logic.TransferServiceImpl;
import com.ironbank.transfer.persistence.DaoFactory;
import com.ironbank.transfer.service.AccountService;
import com.ironbank.transfer.service.AccountValidator;
import com.ironbank.transfer.service.TransactionManager;
import com.ironbank.transfer.service.TransferService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AccountValidatorImpl.class).to(AccountValidator.class).in(Singleton.class);
        bind(TransactionManagerDb.class).to(TransactionManager.class).in(Singleton.class);
        bind(DaoFactoryDb.class).to(DaoFactory.class).in(Singleton.class);
        bind(AccountServiceImpl.class).to(AccountService.class).in(Singleton.class);
        bind(TransferServiceImpl.class).to(TransferService.class).in(Singleton.class);
    }
}
