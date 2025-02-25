package com.bank.bank_demo.dto.response.account;

import java.math.BigDecimal;

public class CreateAccountResponse {
    private long id;
    private BigDecimal balance;
    private String accountNumber;
    private long accountHolderId;
    private long bankId;

    public CreateAccountResponse() {
    }

    public CreateAccountResponse(long id, BigDecimal balance, String accountNumber, long accountHolderId, long bankId) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountHolderId = accountHolderId;
        this.bankId = bankId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }
}

