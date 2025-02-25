package com.bank.bank_demo.repository;

import com.bank.bank_demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
