package com.ironbank.transfer.service;

import java.util.concurrent.Callable;

public interface TransactionManager<Context> {

    <T> T doInTransaction(Callable<T> callable);

    Context getContext();
}

