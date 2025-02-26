package com.bank.bank_demo.controller;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.CreateAccountResponse;
import com.bank.bank_demo.dto.response.account.GetAllAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountWithCustomerId;
import com.bank.bank_demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/create/account")
    public ResponseEntity<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        CreateAccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping(value = "/account/balance/{accountId}")
    public ResponseEntity<GetByAccountBalanceResponse> getBalance(@PathVariable long accountId)
    {
        GetByAccountBalanceResponse response = accountService.getByAccountBalance(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/account/customer/{accountHolderId}")
    public ResponseEntity<List<GetByAccountWithCustomerId>> getAccountWithCustomerId(@PathVariable long accountHolderId) {
        List<GetByAccountWithCustomerId> response = accountService.getByCustomerId(accountHolderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/all/accounts")
    public ResponseEntity<List<GetAllAccountResponse>> getAllAccounts() {
        List<GetAllAccountResponse> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
