package com.alvaro.bank.repository;

import com.alvaro.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByName(String name);
    Boolean existsAccountByName(String name);
}
