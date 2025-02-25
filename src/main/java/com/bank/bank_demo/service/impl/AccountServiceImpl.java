package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.CreateAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;
import com.bank.bank_demo.entity.Account;
import com.bank.bank_demo.mapper.AccountMapper;
import com.bank.bank_demo.repository.AccountRepository;
import com.bank.bank_demo.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        log.info("Creating new account for holder ID: {}", request.getAccountHolderId());
        Account account = AccountMapper.INSTANCE.createAccount(request);
        account.setBalance(BigDecimal.ZERO);
        Random randomAccountNumber = new Random();
        String accountNumber = generateAccountNumber(randomAccountNumber);
        account.setAccountNumber(accountNumber);

        log.info("Generated account number: {}", accountNumber);
        Account savedAccount = accountRepository.save(account);

        log.info("Account created with ID: {}", savedAccount.getId());

        return new CreateAccountResponse(savedAccount.getId(), savedAccount.getBalance(), savedAccount.getAccountNumber(),
                savedAccount.getAccountHolder().getId(), savedAccount.getBank().getId());
    }

    @Override
    public GetByAccountBalanceResponse getByAccountBalance(Long accountId) {
        log.info("Fetching balance for account ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Account not found with ID: {}", accountId);
                    return new EntityNotFoundException("Account not found");
                });

        log.info("Fetched account balance: {}", account.getBalance());
        return AccountMapper.INSTANCE.getByAccountBalance(account);
    }

    private String generateAccountNumber(Random random) {
        String accountNumber = String.format("%010d", random.nextInt(1000000000));
        return accountNumber;
    }
}
