package com.bank.bank_demo.repository;

import com.bank.bank_demo.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
