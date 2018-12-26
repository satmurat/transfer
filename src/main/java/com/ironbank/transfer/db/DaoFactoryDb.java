package com.ironbank.transfer.db;

import com.ironbank.transfer.persistence.AccountDao;
import com.ironbank.transfer.persistence.DaoFactory;
import org.hibernate.Session;

public class DaoFactoryDb implements DaoFactory {

    @Override
    public AccountDao getAccountDao(Object context) {
        return new AccountDaoDb((Session) context);
    }
}
