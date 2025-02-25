package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.entity.AccountHolder;

public interface AccountHolderService {

    CreateAccountHolderResponse createAccountHolder(CreateAccountHolderRequest request);

}
