package com.ironbank.transfer.db;

import com.ironbank.transfer.exception.InvalidOperationException;
import com.ironbank.transfer.exception.UnhandledException;
import com.ironbank.transfer.service.TransactionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionManagerDb implements TransactionManager<Session> {
    private final static Logger logger = Logger.getLogger(TransactionManagerDb.class.getName());

    @Override
    public <T> T doInTransaction(Callable<T> callable) {
        Session session = getContext();
        Transaction tx = null;
        T result;
        try {
            tx = session.beginTransaction();
            result = callable.call();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.log(Level.SEVERE, e.getMessage());
            throw defineException(e);
        }
        return result;
    }

    private RuntimeException defineException(Exception e) {
        return e instanceof InvalidOperationException ? (InvalidOperationException) e : new UnhandledException(e);
    }

    @Override
    public Session getContext() {
        return DbModule.getSessionFactory().getCurrentSession();
    }
}
