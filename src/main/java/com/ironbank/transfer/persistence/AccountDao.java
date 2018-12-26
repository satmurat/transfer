package com.ironbank.transfer.persistence;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.exception.PersistException;

import java.util.UUID;

public interface AccountDao {

    Account loadForUpdate(UUID accountId) throws PersistException;

    void persist(Account account) throws PersistException;

    Account get(UUID accountId) throws PersistException;
}
