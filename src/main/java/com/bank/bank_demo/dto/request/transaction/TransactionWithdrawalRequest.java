package com.bank.bank_demo.dto.request.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransactionWithdrawalRequest {
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Account ID cannot be null")
    private long accountId;

    public TransactionWithdrawalRequest() {
    }

    public TransactionWithdrawalRequest(BigDecimal amount, long accountId) {
        this.amount = amount;
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
