package com.bank.bank_demo.dto.response.transaction;

import com.bank.bank_demo.entity.TransactionType;

import java.math.BigDecimal;

public class TransactionWithdrawalResponse {
    private long id;
    private BigDecimal amount;
    private TransactionType type;
    private long accountId;

    public TransactionWithdrawalResponse() {
    }

    public TransactionWithdrawalResponse(long id, BigDecimal amount, TransactionType type, long accountId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
