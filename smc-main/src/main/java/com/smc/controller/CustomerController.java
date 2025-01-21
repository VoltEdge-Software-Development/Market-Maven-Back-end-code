package com.smc.controller;

import com.smc.dto.TransactionDTO;
import com.smc.dto.TransactionRequestDTO;
import com.smc.exception.TransactionException;
import com.smc.service.customer.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/customer")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    public static final String TRANSACTION_SERVICE ="transactionService";
    @PostMapping("account-opening")
    public ResponseEntity<TransactionDTO> processAccountOpening(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processAccountOpening(requestDTO));
    }

    @PostMapping("withdrawal")
    @CircuitBreaker(name = TRANSACTION_SERVICE,fallbackMethod = "handleException")
    public ResponseEntity<TransactionDTO> processWithdrawal(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processWithdrawal(requestDTO));
    }

    @PostMapping("transfer-to-trading-account")
    public ResponseEntity<TransactionDTO> processTransferToTradingAccount(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processTransferToTradingAccount(requestDTO));
    }

    @PostMapping("transfer-from-trading-account")
    public ResponseEntity<TransactionDTO> processTransferFromTradingAccount(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processTransferFromTradingAccount(requestDTO));
    }

    @PostMapping("deposit")
    public ResponseEntity<TransactionDTO> processDeposit(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processDeposit(requestDTO));
    }
    @PostMapping("balance-enquiry")
    public ResponseEntity<TransactionDTO> processBalanceEnquiry(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.processBalanceEnquiry(requestDTO));
    }
    ResponseEntity<TransactionDTO> handleException(Exception e){
        throw new TransactionException("Service not available. Please try again later");
    }
}
