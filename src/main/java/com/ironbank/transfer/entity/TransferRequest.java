package com.ironbank.transfer.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequest {
    private UUID fromId;
    private UUID toId;
    private BigDecimal amount;

    public TransferRequest() {
    }

    public TransferRequest(UUID fromId, UUID toId, BigDecimal amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public UUID getFromId() {
        return fromId;
    }

    public void setFromId(UUID fromId) {
        this.fromId = fromId;
    }

    public UUID getToId() {
        return toId;
    }

    public void setToId(UUID toId) {
        this.toId = toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
