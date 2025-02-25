package com.bank.bank_demo.dto.response.account;

import java.math.BigDecimal;

public class GetByAccountBalanceResponse {
    private BigDecimal balance;

    public GetByAccountBalanceResponse() {
    }

    public GetByAccountBalanceResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
