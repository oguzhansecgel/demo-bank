package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.CreateBankResponse;
import com.bank.bank_demo.dto.response.bank.GetAllBankResponse;
import com.bank.bank_demo.dto.response.bank.GetByIdBankResponse;
import com.bank.bank_demo.dto.response.bank.UpdateBankResponse;

import java.util.List;

public interface BankService {

    CreateBankResponse createBank(CreateBankRequest request);
    UpdateBankResponse updateBank(UpdateBankRequest request, Long bankId);
    GetByIdBankResponse getByIdBank(Long bankId);
    List<GetAllBankResponse> getAllBanks();
    void deleteBank(Long bankId);
}
