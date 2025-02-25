package com.bank.bank_demo.repository;

import com.bank.bank_demo.entity.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
