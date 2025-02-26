package com.bank.bank_demo.controller;

import com.bank.bank_demo.dto.request.transaction.TransactionDepositRequest;
import com.bank.bank_demo.dto.request.transaction.TransactionWithdrawalRequest;
import com.bank.bank_demo.dto.response.transaction.TransactionDepositResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionHistoryResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionWithdrawalResponse;
import com.bank.bank_demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDepositResponse> deposit(@Valid @RequestBody TransactionDepositRequest transactionRequest) {
        TransactionDepositResponse response = transactionService.deposit(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<TransactionWithdrawalResponse> withdrawal(@Valid @RequestBody TransactionWithdrawalRequest transactionRequest) {
        TransactionWithdrawalResponse response = transactionService.withdrawal(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory(
            @PathVariable("accountId") Long accountId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        List<TransactionHistoryResponse> transactionHistory = transactionService.getTransactionHistory(accountId, startDate, endDate);
        return ResponseEntity.ok(transactionHistory);
    }


}
