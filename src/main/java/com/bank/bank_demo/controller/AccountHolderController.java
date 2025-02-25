package com.bank.bank_demo.controller;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.service.AccountHolderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accountHolder")
public class AccountHolderController
{
    private final AccountHolderService accountHolderService;

    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }
    @PostMapping("/create/accountHolder")
    public ResponseEntity<CreateAccountHolderResponse> createAccountHolder(@Valid @RequestBody CreateAccountHolderRequest request) {
        CreateAccountHolderResponse response = accountHolderService.createAccountHolder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
