package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.transaction.TransactionDepositRequest;
import com.bank.bank_demo.dto.request.transaction.TransactionWithdrawalRequest;
import com.bank.bank_demo.dto.response.transaction.TransactionDepositResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionHistoryResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionWithdrawalResponse;
import com.bank.bank_demo.entity.Account;
import com.bank.bank_demo.entity.Transaction;
import com.bank.bank_demo.entity.TransactionType;
import com.bank.bank_demo.exception.type.InsufficientBalanceException;
import com.bank.bank_demo.exception.type.NotFoundAccountException;
import com.bank.bank_demo.mapper.TransactionMapper;
import com.bank.bank_demo.repository.AccountRepository;
import com.bank.bank_demo.repository.TransactionRepository;
import com.bank.bank_demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@EnableRetry
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);



    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountService;
    }
    //kilitli
    @Transactional
    protected Transaction createTransaction(BigDecimal amount, Long accountId, TransactionType transactionType) {
        Optional<Account> accountExisting = accountRepository.findByIdWithLock(accountId); // Hesabı güncelleme kilidiyle alıyoruz
        if (accountExisting.isEmpty()) {
            log.error("Account not found: {}", accountId);
            throw new NotFoundAccountException("Account Not Found : " + accountId);
        }

        Account account = accountExisting.get();
        log.info("[Thread: {}] With Lock - Before: Account {} balance: {}",
                Thread.currentThread().getId(), accountId, account.getBalance());
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(transactionType);
        transaction.setAccount(account);
        transaction.setAmount(amount);

        if (transactionType == TransactionType.WITHDRAWAL) {
            BigDecimal currentBalance = account.getBalance();
            if (currentBalance.compareTo(amount) < 0) {
                log.error("Insufficient balance for account ID: {}", accountId);
                throw new InsufficientBalanceException("Your balance is insufficient.");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }
        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }


    // Para çekme
    @Override
    @CacheEvict(value = "transactionHistory", key = "'ALL_TRANSACTIONS_' + #transactionRequest.accountId")
    @Retryable(value = InsufficientBalanceException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public TransactionWithdrawalResponse withdrawal(TransactionWithdrawalRequest transactionRequest) {
        log.info("Withdrawing amount: {} from account ID: {} (Attempt starting)",
                transactionRequest.getAmount(), transactionRequest.getAccountId());
        try {
            Transaction savedTransaction = createTransaction(transactionRequest.getAmount(),
                    transactionRequest.getAccountId(),
                    TransactionType.WITHDRAWAL);
            log.info("Withdrawal transaction created with ID: {}, Amount: {}",
                    savedTransaction.getId(), savedTransaction.getAmount());
            return new TransactionWithdrawalResponse(savedTransaction.getId(), savedTransaction.getAmount(),
                    savedTransaction.getType(), savedTransaction.getAccount().getId());
        } catch (InsufficientBalanceException e) {
            log.warn("Retry attempt for amount: {} on account ID: {}",
                    transactionRequest.getAmount(), transactionRequest.getAccountId());
            throw e;
        }
    }

    // Para yatırma
    @Override
    @CacheEvict(value = "transactionHistory", key = "'ALL_TRANSACTIONS_' + #transactionRequest.accountId")
    public TransactionDepositResponse deposit(TransactionDepositRequest transactionRequest) {
        log.info("Depositing amount: {} to account ID: {}", transactionRequest.getAmount(), transactionRequest.getAccountId());

        Transaction savedTransaction = createTransaction(transactionRequest.getAmount(),
                transactionRequest.getAccountId(),
                TransactionType.DEPOSIT);

        log.info("Deposit transaction created with ID: {}, Amount: {}", savedTransaction.getId(), savedTransaction.getAmount());

        return new TransactionDepositResponse(savedTransaction.getId(), savedTransaction.getAmount(),
                savedTransaction.getType(), savedTransaction.getAccount().getId());
    }



    @Override
    @Cacheable(value = "transactionHistory", key = "'ALL_TRANSACTIONS_' + #accountId")
    public List<TransactionHistoryResponse> getTransactionHistory(Long accountId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching transaction history for account ID: {} from {} to {}", accountId, startDate, endDate);
        List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionDateBetween(accountId, startDate, endDate);
        log.info("Fetched {} transactions for account {}", transactions.size(), accountId);
        return TransactionMapper.INSTANCE.transactionHistory(transactions);
    }



    //kilitsiz
   /* @Transactional
    protected Transaction createTransactionWithoutLock(BigDecimal amount, Long accountId, TransactionType transactionType) {
        Optional<Account> accountExisting = accountRepository.findById(accountId);
        if (accountExisting.isEmpty()) {
            log.error("Account not found: {}", accountId);
            throw new NotFoundAccountException("Account Not Found : " + accountId);
        }

        Account account = accountExisting.get();
        log.info("[Thread: {}] With Lock - Before: Account {} balance: {}",
                Thread.currentThread().getId(), accountId, account.getBalance());
        log.info("Without Lock - Before: Account {} balance: {}", accountId, account.getBalance());

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(transactionType);
        transaction.setAccount(account);
        transaction.setAmount(amount);

        if (transactionType == TransactionType.WITHDRAWAL) {
            BigDecimal currentBalance = account.getBalance();
            if (currentBalance.compareTo(amount) < 0) {
                log.error("Insufficient balance for account ID: {}", accountId);
                throw new InsufficientBalanceException("Your balance is insufficient.");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }
        accountRepository.save(account);
        log.info("Without Lock - After: Account {} balance: {}", accountId, account.getBalance());

        return transactionRepository.save(transaction);
    }*/
}
