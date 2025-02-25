package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.transaction.TransactionDepositRequest;
import com.bank.bank_demo.dto.request.transaction.TransactionWithdrawalRequest;
import com.bank.bank_demo.dto.response.transaction.TransactionDepositResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionHistoryResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionWithdrawalResponse;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionDepositResponse deposit(TransactionDepositRequest transactionRequest);
    TransactionWithdrawalResponse withdrawal(TransactionWithdrawalRequest transactionRequest);
    List<TransactionHistoryResponse> getTransactionHistory(LocalDate startDate, LocalDate endDate);
}
