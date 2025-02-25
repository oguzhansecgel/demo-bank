package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.entity.AccountHolder;
import com.bank.bank_demo.mapper.AccountHolderMapper;
import com.bank.bank_demo.repository.AccountHolderRepository;
import com.bank.bank_demo.service.AccountHolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {


    private final AccountHolderRepository accountHolderRepository;
    private Logger log = LoggerFactory.getLogger(AccountHolderMapper.class);
    public AccountHolderServiceImpl(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }


    @Override
    public CreateAccountHolderResponse createAccountHolder(CreateAccountHolderRequest request) {
        log.info("Creating new account holder: {}", request.getHolderName());
        AccountHolder accountHolder = AccountHolderMapper.INSTANCE.createAccountHolder(request);
        AccountHolder savedAccountHolder = accountHolderRepository.save(accountHolder);

        log.info("Account holder created with ID: {}", savedAccountHolder.getId());

        return new CreateAccountHolderResponse(savedAccountHolder.getId(), savedAccountHolder.getHolderName(),
                savedAccountHolder.getHolderSurname(), savedAccountHolder.getIdentityNumber());
    }
}
