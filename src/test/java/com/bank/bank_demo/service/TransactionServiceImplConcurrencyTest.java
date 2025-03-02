package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.transaction.TransactionDepositRequest;
import com.bank.bank_demo.dto.request.transaction.TransactionWithdrawalRequest;
import com.bank.bank_demo.entity.Account;
import com.bank.bank_demo.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class TransactionServiceImplConcurrencyTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    private Long accountId;

    @BeforeEach
    @Transactional
    void setUp() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100));
        account = accountRepository.save(account);
        accountId = account.getId();
    }

    @Test
    void testConcurrentTransactions() throws InterruptedException {
        int threadCount = 3;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        executor.submit(() -> {
            try {
                TransactionWithdrawalRequest request = new TransactionWithdrawalRequest( BigDecimal.valueOf(100),accountId);
                transactionService.withdrawal(request);
                System.out.println("Withdrawal 100 successful");
            } catch (Exception e) {
                System.out.println("Withdrawal 100 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                TransactionWithdrawalRequest request = new TransactionWithdrawalRequest(BigDecimal.valueOf(30),accountId);
                transactionService.withdrawal(request);
                System.out.println("Withdrawal 30 successful");
            } catch (Exception e) {
                System.out.println("Withdrawal 30 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                TransactionDepositRequest request = new TransactionDepositRequest(BigDecimal.valueOf(50),accountId);
                transactionService.deposit(request);
                System.out.println("Deposit 50 successful");
            } catch (Exception e) {
                System.out.println("Deposit 50 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await();
        executor.shutdown();

        Account account = accountRepository.findById(accountId).orElseThrow();
        assertEquals(BigDecimal.valueOf(20).setScale(2), account.getBalance(), "Final balance should be 20");
    }
}