package com.ironbank.transfer.persistence;

public interface DaoFactory {

    AccountDao getAccountDao(Object context);
}
