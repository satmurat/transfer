package com.ironbank.transfer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        ExceptionResponse response = new ExceptionResponse(e.getClass().getSimpleName(), e.getMessage());
        return Response.status(e.getResponse().getStatus()).entity(response).build();
    }
}
