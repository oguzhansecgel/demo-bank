package com.bank.bank_demo.dto.request.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {
    @NotNull(message = "Account holder ID cannot be null")
    @Min(value = 1, message = "Account holder ID must be a positive value")
    private Long accountHolderId;

    @NotNull(message = "Bank ID cannot be null")
    @Min(value = 1, message = "Bank ID must be a positive value")
    private Long bankId;
    public CreateAccountRequest() {
    }

    public CreateAccountRequest(long accountHolderId, long bankId) {
        this.accountHolderId = accountHolderId;
        this.bankId = bankId;
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
