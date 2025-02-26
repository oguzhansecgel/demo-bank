package com.bank.bank_demo.controller;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.CreateBankResponse;
import com.bank.bank_demo.dto.response.bank.GetAllBankResponse;
import com.bank.bank_demo.dto.response.bank.GetByIdBankResponse;
import com.bank.bank_demo.dto.response.bank.UpdateBankResponse;
import com.bank.bank_demo.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<CreateBankResponse> createBank(@Valid @RequestBody CreateBankRequest request) {
        CreateBankResponse response = bankService.createBank(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/bank/{id}")
    public ResponseEntity<UpdateBankResponse> updateBank(@PathVariable Long id, @Valid @RequestBody UpdateBankRequest request) {
        UpdateBankResponse response = bankService.updateBank(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdBankResponse> getByIdBank(@PathVariable Long id) {
        GetByIdBankResponse response = bankService.getByIdBank(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/all/banks")
    public ResponseEntity<List<GetAllBankResponse>> getAllBanks() {
        List<GetAllBankResponse> response = bankService.getAllBanks();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
