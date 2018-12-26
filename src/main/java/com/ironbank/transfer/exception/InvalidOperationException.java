package com.ironbank.transfer.exception;

import javax.ws.rs.BadRequestException;

public class InvalidOperationException extends BadRequestException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
