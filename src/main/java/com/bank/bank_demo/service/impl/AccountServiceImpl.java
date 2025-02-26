package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.CreateAccountResponse;
import com.bank.bank_demo.dto.response.account.GetAllAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountWithCustomerId;
import com.bank.bank_demo.entity.Account;
import com.bank.bank_demo.mapper.AccountMapper;
import com.bank.bank_demo.repository.AccountRepository;
import com.bank.bank_demo.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = AccountMapper.INSTANCE;
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        log.info("Creating new account for holder ID: {}", request.getAccountHolderId());
        Account account = accountMapper.createAccount(request);
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
        return accountMapper.getByAccountBalance(account);
    }

    @Override
    public List<GetByAccountWithCustomerId> getByCustomerId(Long customerId) {
        log.info("Fetching accounts for customer ID: {}", customerId);
        List<Account> accounts = accountRepository.findByAccountHolderId(customerId);  // Burada müşteri ID'sine göre hesapları buluyoruz.

        if (accounts.isEmpty()) {
            log.error("No accounts found for customer ID: {}", customerId);
            throw new EntityNotFoundException("No accounts found for this customer");
        }

        return accountMapper.getByAccountWithCustomerIdToList(accounts);
    }

    @Override
    public List<GetAllAccountResponse> getAllAccounts() {
        log.info("Fetching all accounts.");
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.getAllAccountToListResponse(accounts);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isPresent()) {
            accountRepository.deleteById(accountId);
            log.info("Account with id {} has been successfully deleted.", accountId);
        } else {
            log.warn("Account with id {} not found. Deletion failed.", accountId);
        }
    }


    private String generateAccountNumber(Random random) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
