package com.bank.bank_demo.controller;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.request.accountHolder.UpdateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetAllAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetByIdAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.UpdateAccountHolderResponse;
import com.bank.bank_demo.service.AccountHolderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accountHolder")
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAccountHolderResponse> createAccountHolder(@Valid @RequestBody CreateAccountHolderRequest request) {
        CreateAccountHolderResponse response = accountHolderService.createAccountHolder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateAccountHolderResponse> updateAccountHolder(@Valid @RequestBody UpdateAccountHolderRequest request, @PathVariable Long id) {
        UpdateAccountHolderResponse response = accountHolderService.updateAccountHolder(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetByIdAccountHolderResponse> getByIdAccountHolder(@PathVariable Long id) {
        GetByIdAccountHolderResponse response = accountHolderService.getByIdAccountHolder(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetAllAccountHolderResponse>> getAllAccountHolders() {
        List<GetAllAccountHolderResponse> response = accountHolderService.getAllAccountHolders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{accountHolderId}")
    public void deleteAccountHolder(@PathVariable Long accountHolderId)
    {
        accountHolderService.deleteAccountHolder(accountHolderId);
    }
}
