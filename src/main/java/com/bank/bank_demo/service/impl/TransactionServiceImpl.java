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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountService;
    }

    @Override
    @CacheEvict(value = "transactionHistory", key = "'ALL_TRANSACTIONS'")
    public TransactionDepositResponse deposit(TransactionDepositRequest transactionRequest) {
        log.info("Depositing amount: {} to account ID: {}", transactionRequest.getAmount(), transactionRequest.getAccountId());
        Optional<Account> accountExisting = accountRepository.findById(transactionRequest.getAccountId());
        if(accountExisting.isEmpty()) {
            log.error("Account not found: {}", transactionRequest.getAccountId());
            throw new NotFoundAccountException("Account Not Found : " + transactionRequest.getAccountId());
        }

        Transaction transaction = TransactionMapper.INSTANCE.transactionDeposit(transactionRequest);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(TransactionType.DEPOSIT);
        Account account = accountExisting.get();
        account.setBalance(account.getBalance().add(transactionRequest.getAmount()));

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Deposit transaction created with ID: {}, Amount: {}", savedTransaction.getId(), savedTransaction.getAmount());

        return new TransactionDepositResponse(savedTransaction.getId(), savedTransaction.getAmount(),
                savedTransaction.getType(), savedTransaction.getAccount().getId());
    }

    // para çekme
    @Override
    @CacheEvict(value = "transactionHistory", key = "'ALL_TRANSACTIONS'")
    public TransactionWithdrawalResponse withdrawal(TransactionWithdrawalRequest transactionRequest) {
        log.info("Withdrawing amount: {} from account ID: {}", transactionRequest.getAmount(), transactionRequest.getAccountId());
        Optional<Account> accountExisting = accountRepository.findById(transactionRequest.getAccountId());
        if(accountExisting.isEmpty()) {
            log.error("Account not found: {}", transactionRequest.getAccountId());
            throw new NotFoundAccountException("Account Not Found : " + transactionRequest.getAccountId());
        }

        Transaction transaction = TransactionMapper.INSTANCE.transactionWithdrawal(transactionRequest);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(TransactionType.WITHDRAWAL);
        Account account = accountExisting.get();
        BigDecimal currentBalance = account.getBalance();
        BigDecimal amountToWithdraw = transactionRequest.getAmount();
        if (currentBalance.compareTo(amountToWithdraw) < 0) {
            log.error("Insufficient balance for account ID: {}", transactionRequest.getAccountId());
            throw new InsufficientBalanceException("Your balance is insufficient.");
        }

        account.setBalance(account.getBalance().subtract(transactionRequest.getAmount()));
        Transaction savedTransaction = transactionRepository.save(transaction);

        log.info("Withdrawal transaction created with ID: {}, Amount: {}", savedTransaction.getId(), savedTransaction.getAmount());

        return new TransactionWithdrawalResponse(savedTransaction.getId(), savedTransaction.getAmount(),
                savedTransaction.getType(), savedTransaction.getAccount().getId());
    }

    @Override
    @Cacheable(value = "transactionHistory", key = "'ALL_TRANSACTIONS'")
    public List<TransactionHistoryResponse> getTransactionHistory(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching transaction history from {} to {}", startDate, endDate);
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        log.info("Fetched {} transactions", transactions.size());
        return TransactionMapper.INSTANCE.transactionHistory(transactions);
    }
}
