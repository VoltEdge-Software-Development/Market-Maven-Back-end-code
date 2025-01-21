package com.smc.repository;

import com.smc.entity.user.Account;
import com.smc.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
