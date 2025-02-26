package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.request.accountHolder.UpdateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetAllAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetByIdAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.UpdateAccountHolderResponse;

import java.util.List;

public interface AccountHolderService {

    CreateAccountHolderResponse createAccountHolder(CreateAccountHolderRequest request);
    UpdateAccountHolderResponse updateAccountHolder(UpdateAccountHolderRequest request, Long id);
    GetByIdAccountHolderResponse getByIdAccountHolder(Long id);
    List<GetAllAccountHolderResponse> getAllAccountHolders();
    void deleteAccountHolder(Long id);
}
