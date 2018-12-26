package com.ironbank.transfer.service;

import com.ironbank.transfer.entity.Account;

import java.util.UUID;

public interface AccountService {

    Account get(UUID id);

    Account save(Account account);
}
