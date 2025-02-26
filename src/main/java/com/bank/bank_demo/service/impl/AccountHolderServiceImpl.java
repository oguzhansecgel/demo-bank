package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.request.accountHolder.UpdateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetAllAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetByIdAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.UpdateAccountHolderResponse;
import com.bank.bank_demo.entity.AccountHolder;
import com.bank.bank_demo.mapper.AccountHolderMapper;
import com.bank.bank_demo.repository.AccountHolderRepository;
import com.bank.bank_demo.service.AccountHolderService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UpdateAccountHolderResponse updateAccountHolder(UpdateAccountHolderRequest request, Long id) {
        AccountHolder existingAccountHolder = accountHolderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountHolder not found with id: " + id));

        // Güncelleme işlemini yap
        existingAccountHolder.setHolderName(request.getHolderName());
        existingAccountHolder.setHolderSurname(request.getHolderSurname());
        existingAccountHolder.setIdentityNumber(request.getIdentityNumber());

        AccountHolder updatedAccountHolder = accountHolderRepository.save(existingAccountHolder);

        return new UpdateAccountHolderResponse(updatedAccountHolder.getId(), updatedAccountHolder.getHolderName(), updatedAccountHolder.getHolderSurname(),updatedAccountHolder.getIdentityNumber());
    }

    @Override
    public GetByIdAccountHolderResponse getByIdAccountHolder(Long id) {
        log.info("Fetching account holder with ID: {}", id);
        AccountHolder accountHolder = accountHolderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Account holder not found with ID: {}", id);
                    return new EntityNotFoundException("AccountHolder not found");
                });

        return AccountHolderMapper.INSTANCE.getByIdAccountHolder(accountHolder);
    }

    @Override
    public List<GetAllAccountHolderResponse> getAllAccountHolders() {
        log.info("Fetching all account holders");
        List<AccountHolder> accountHolders = accountHolderRepository.findAll();

        return AccountHolderMapper.INSTANCE.getAllAccountHoldersToList(accountHolders);
    }

    @Override
    public void deleteAccountHolder(Long id) {
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);

        if (accountHolderOptional.isPresent()) {
            accountHolderRepository.deleteById(id);
            log.info("AccountHolder with id {} has been successfully deleted.", id);
        } else {
            log.warn("AccountHolder with id {} not found. Deletion failed.", id);
        }
    }


}
