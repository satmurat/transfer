package com.ironbank.transfer.exception;

public class PersistException extends RuntimeException {
    public PersistException(Exception e) {
        super(e);
    }
}
