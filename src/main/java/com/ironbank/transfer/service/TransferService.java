package com.ironbank.transfer.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferService {
    void transfer(UUID fromId, UUID toId, BigDecimal amount);
}
