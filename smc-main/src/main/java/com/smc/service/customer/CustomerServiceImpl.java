package com.smc.service.customer;

import com.smc.dto.TransactionDTO;
import com.smc.dto.TransactionRequestDTO;
import com.smc.entity.user.Account;
import com.smc.exception.TransactionException;
import com.smc.repository.AccountRepository;
import com.smc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import static com.smc.util.Constants.SUCCESSFUL;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionDTO processDeposit(TransactionRequestDTO requestDTO) {
        String username = getUsername();
        Account mainAccount = getMainAccount(username);
        mainAccount.setAmount(requestDTO.getAmount().add(mainAccount.getAmount()));
        mainAccount = accountRepository.saveAndFlush(mainAccount);
        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Transaction Successful")
                .balance(mainAccount.getAmount())
                .accountNumber(mainAccount.getAccountNumber())
                .accountName(mainAccount.getAccountName())
                .build();
    }

    @Override
    public TransactionDTO processWithdrawal(TransactionRequestDTO requestDTO) {
        String username = getUsername();
        Account mainAccount = getMainAccount(username);
        mainAccount.setAmount(mainAccount.getAmount().subtract(requestDTO.getAmount()));
        mainAccount = accountRepository.saveAndFlush(mainAccount);
        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Transaction Successful")
                .balance(mainAccount.getAmount())
                .accountNumber(mainAccount.getAccountNumber())
                .accountName(mainAccount.getAccountName())
                .build();
    }

    @Override
    public TransactionDTO processTransferToTradingAccount(TransactionRequestDTO requestDTO) {
        String username = getUsername();
        Account mainAccount = getMainAccount(username);
        BigDecimal amount = mainAccount.getAmount().subtract(requestDTO.getAmount());
        mainAccount.setAmount(amount);
        mainAccount = accountRepository.saveAndFlush(mainAccount);
        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Transaction Successful")
                .balance(mainAccount.getAmount())
                .accountNumber(mainAccount.getAccountNumber())
                .accountName(mainAccount.getAccountName())
                .build();
    }

    @Override
    public TransactionDTO processTransferFromTradingAccount(TransactionRequestDTO requestDTO) {
        String username = getUsername();
        Account mainAccount = getMainAccount(username);
        mainAccount.setAmount(requestDTO.getAmount().add(mainAccount.getAmount()));
        mainAccount = accountRepository.saveAndFlush(mainAccount);
        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Transaction Successful")
                .balance(mainAccount.getAmount())
                .accountNumber(mainAccount.getAccountNumber())
                .accountName(mainAccount.getAccountName())
                .build();
    }

    @Override
    public TransactionDTO processBalanceEnquiry(TransactionRequestDTO requestDTO) {
        var account = accountRepository.findAccountByAccountNumber(requestDTO.getAccountNumber()).orElseThrow(() -> new TransactionException("Account not found"));
        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Transaction Successful")
                .balance(account.getAmount())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .build();
    }

    @Override
    public TransactionDTO processAccountOpening(TransactionRequestDTO requestDTO) {
        String username = getUsername();
        var customer = userRepository.findByEmail(username).orElseThrow(() -> new TransactionException("Customer not found"));
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        Account account = Account.
                builder()
                .accountName(customer.getFirstname().concat(" ".concat(customer.getLastname())))
                .accountNumber("MT" + datetime)
                .currency(requestDTO.getCurrency())
                .amount(new BigDecimal(0))
                .user(customer)
                .build();
        account = accountRepository.saveAndFlush(account);

        return TransactionDTO.builder()
                .responseCode(SUCCESSFUL)
                .responseDescription("Account created successful")
                .balance(account.getAmount())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .build();
    }

    private Account getMainAccount(String username) {
        var customer = userRepository.findByEmail(username).orElseThrow(() -> new TransactionException("Customer not found"));
        return customer.getAccounts().stream().filter((e -> e.getAccountType().equalsIgnoreCase("MAIN"))).findAny().orElseThrow(() -> new TransactionException("No main account found"));
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
