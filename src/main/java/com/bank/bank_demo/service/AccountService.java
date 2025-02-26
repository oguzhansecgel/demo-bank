package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.CreateAccountResponse;
import com.bank.bank_demo.dto.response.account.GetAllAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountWithCustomerId;

import java.util.List;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest request);
    GetByAccountBalanceResponse getByAccountBalance(Long accountId);
    List<GetByAccountWithCustomerId> getByCustomerId(Long customerId);
    List<GetAllAccountResponse> getAllAccounts();
    void deleteAccount(Long accountId);
}
