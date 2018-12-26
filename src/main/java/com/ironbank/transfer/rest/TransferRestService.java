package com.ironbank.transfer.rest;

import com.ironbank.transfer.entity.TransferRequest;
import com.ironbank.transfer.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("transfer")
public class TransferRestService {
    @Inject
    private TransferService transferService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(TransferRequest transfer) {
        transferService.transfer(transfer.getFromId(), transfer.getToId(), transfer.getAmount());
        return Response.noContent().build();
    }

}
