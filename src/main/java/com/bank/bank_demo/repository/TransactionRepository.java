package com.bank.bank_demo.repository;

import com.bank.bank_demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdAndTransactionDateBetween(Long accountId, LocalDate startDate, LocalDate endDate);


}
