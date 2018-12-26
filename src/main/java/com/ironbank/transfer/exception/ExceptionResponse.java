package com.ironbank.transfer.exception;

public class ExceptionResponse {
    private String type;
    private String error;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String type, String error) {
        this.type = type;
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
