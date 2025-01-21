package com.smc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransactionRequestDTO {
    private BigDecimal amount;
    private String mainAccountNumber;
    private String tradingAccountNumber;
    private String accountNumber;
    private String currency;
}
