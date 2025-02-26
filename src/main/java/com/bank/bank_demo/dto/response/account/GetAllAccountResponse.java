package com.bank.bank_demo.dto.response.account;

import java.math.BigDecimal;

public class GetAllAccountResponse {
    private long id;
    private BigDecimal balance;
    private String accountNumber;

    private long bankId;
    private long accountHolderId;

    public GetAllAccountResponse() {
    }

    public GetAllAccountResponse(long id, BigDecimal balance, String accountNumber, long bankId, long accountHolderId) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.bankId = bankId;
        this.accountHolderId = accountHolderId;
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

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
