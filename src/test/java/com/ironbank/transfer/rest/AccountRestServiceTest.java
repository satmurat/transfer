package com.ironbank.transfer.rest;

import com.ironbank.transfer.entity.Account;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountRestServiceTest extends BaseJerseyTest {

    @Test
    public void accountCreateTest() {
        // PREPARE, ACT
        Response response = createAccount();
        // ASSERT
        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
        Account account = response.readEntity(Account.class);
        Assert.assertNotNull(account.getId());
    }

    @Test
    public void accountGetTest() {
        // PREPARE
        Account account = createAccount().readEntity(Account.class);
        // ACT
        Response response = target("/accounts/" + account.getId())
                .request(MediaType.APPLICATION_JSON).get();
        // ASSERT
        Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }

    @Test
    public void tryGetNotExistsAccountTest() {
        // ACT
        Response response = target("/accounts/" + UUID.randomUUID())
                .request(MediaType.APPLICATION_JSON).get();
        // ASSERT
        Assert.assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());
    }

    private Response createAccount() {
        Account account = new Account();
        account.setAmount(new BigDecimal(1000));
        return target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
    }
}
