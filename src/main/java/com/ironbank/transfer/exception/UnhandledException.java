package com.ironbank.transfer.exception;

import javax.ws.rs.InternalServerErrorException;

public class UnhandledException extends InternalServerErrorException {
    public UnhandledException(Exception e) {
        super(e.getMessage());
    }
}
