package com.ironbank.transfer.rest;

import com.ironbank.transfer.entity.Account;
import com.ironbank.transfer.entity.TransferRequest;
import com.ironbank.transfer.exception.ExceptionResponse;
import com.ironbank.transfer.exception.InvalidOperationException;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

public class TransferRestServiceTest extends BaseJerseyTest {
    private Account poorAccount;
    private Account richAccount;
    private final BigDecimal TRANSFER_AMOUNT = new BigDecimal(1000);

    @Before
    public void setUp() throws Exception {
        super.setUp();
        poorAccount = new Account(UUID.randomUUID(), new BigDecimal(100));
        richAccount = new Account(UUID.randomUUID(), new BigDecimal(1000000));
        // PREPARE
        target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(poorAccount, MediaType.APPLICATION_JSON));
        target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(richAccount, MediaType.APPLICATION_JSON));
    }

    @Test
    public void transferSuccessTest() {
        // PREPARE
        TransferRequest transferRequest = new TransferRequest(richAccount.getId(), poorAccount.getId(), TRANSFER_AMOUNT);
        // ACT
        Response response = target("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequest, MediaType.APPLICATION_JSON));
        // ASSERT
        Assert.assertEquals(HttpStatus.NO_CONTENT_204, response.getStatus());
    }

    @Test
    public void transferFailTest() {
        // PREPARE
        TransferRequest transferRequest = new TransferRequest(poorAccount.getId(), richAccount.getId(), TRANSFER_AMOUNT);
        // ACT
        Response response = target("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequest, MediaType.APPLICATION_JSON));
        ExceptionResponse responseBody = response.readEntity(ExceptionResponse.class);
        // ASSERT
        Assert.assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
        Assert.assertEquals(InvalidOperationException.class.getSimpleName(), responseBody.getType());
    }
}
