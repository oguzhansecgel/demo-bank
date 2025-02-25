package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.CreateBankResponse;
import com.bank.bank_demo.dto.response.bank.GetAllBankResponse;
import com.bank.bank_demo.dto.response.bank.GetByIdBankResponse;
import com.bank.bank_demo.dto.response.bank.UpdateBankResponse;
import com.bank.bank_demo.entity.Bank;
import com.bank.bank_demo.mapper.BankMapper;
import com.bank.bank_demo.repository.BankRepository;
import com.bank.bank_demo.service.BankService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private Logger log = LoggerFactory.getLogger(BankServiceImpl.class);
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public CreateBankResponse createBank(CreateBankRequest request) {
        log.info("Creating new bank with name: {}", request.getName());
        Bank bank = BankMapper.INSTANCE.createBank(request);
        Bank savedBank = bankRepository.save(bank);

        log.info("Bank created with ID: {}, Name: {}", savedBank.getId(), savedBank.getName());

        return new CreateBankResponse(savedBank.getId(), savedBank.getName(), savedBank.getCode());
    }

    @Override
    public UpdateBankResponse updateBank(UpdateBankRequest request, Long bankId) {
        log.info("Updating bank with ID: {}", bankId);
        Bank existingBank = bankRepository.findById(bankId)
                .orElseThrow(() -> {
                    log.error("Bank not found with ID: {}", bankId);
                    return new EntityNotFoundException("Bank not found");
                });

        BankMapper.INSTANCE.updateBank(request, existingBank);
        Bank updatedBank = bankRepository.save(existingBank);

        log.info("Bank updated with ID: {}, Name: {}", updatedBank.getId(), updatedBank.getName());

        return new UpdateBankResponse(updatedBank.getId(), updatedBank.getName(), updatedBank.getCode());
    }

    @Override
    public GetByIdBankResponse getByIdBank(Long bankId) {
        log.info("Fetching bank with ID: {}", bankId);
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> {
                    log.error("Bank not found with ID: {}", bankId);
                    return new EntityNotFoundException("Bank not found");
                });

        log.info("Fetched bank with ID: {}", bank.getId());
        return BankMapper.INSTANCE.getByIdBank(bank);
    }

    @Override
    public List<GetAllBankResponse> getAllBanks() {
        log.info("Fetching all banks");
        List<Bank> banks = bankRepository.findAll();
        log.info("Fetched {} banks", banks.size());
        return BankMapper.INSTANCE.getAllBanksToList(banks);
    }

    @Override
    public void deleteBank(Long bankId) {
        log.info("Deleting bank with ID: {}", bankId);
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> {
                    log.error("Bank not found with ID: {}", bankId);
                    return new EntityNotFoundException("Bank not found");
                });

        bankRepository.delete(bank);
        log.info("Bank with ID: {} deleted successfully", bankId);
    }
}
