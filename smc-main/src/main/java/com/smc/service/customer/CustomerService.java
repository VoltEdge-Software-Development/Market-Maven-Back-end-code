package com.smc.service.customer;

import com.smc.dto.TransactionDTO;
import com.smc.dto.TransactionRequestDTO;

public interface CustomerService {
    TransactionDTO processDeposit(TransactionRequestDTO requestDTO);
    TransactionDTO processWithdrawal(TransactionRequestDTO requestDTO);
    TransactionDTO processTransferToTradingAccount(TransactionRequestDTO requestDTO);
    TransactionDTO processTransferFromTradingAccount(TransactionRequestDTO requestDTO);
    TransactionDTO processBalanceEnquiry(TransactionRequestDTO requestDTO);
    TransactionDTO processAccountOpening(TransactionRequestDTO requestDTO);
}
