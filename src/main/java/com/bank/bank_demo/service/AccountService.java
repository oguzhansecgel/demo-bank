package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.CreateAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest request);
    GetByAccountBalanceResponse getByAccountBalance(Long accountId);
}
