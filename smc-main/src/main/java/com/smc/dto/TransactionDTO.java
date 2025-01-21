package com.smc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransactionDTO {
    private String responseCode;
    private String responseDescription;
    private BigDecimal balance;
    private String accountNumber;
    private String accountName;
    private String currency;
}
