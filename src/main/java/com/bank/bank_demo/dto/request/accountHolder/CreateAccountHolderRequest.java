package com.bank.bank_demo.dto.request.accountHolder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAccountHolderRequest {
    @NotBlank(message = "Holder name cannot be blank")
    private String holderName;

    @NotBlank(message = "Holder surname cannot be blank")
    private String holderSurname;

    @NotBlank(message = "Identity number cannot be blank")
    @Size(min = 11, max = 11, message = "Identity number must be 11 characters long")
    private String identityNumber;

    public CreateAccountHolderRequest() {
    }

    public CreateAccountHolderRequest(String holderName, String holderSurname, String identityNumber) {
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.identityNumber = identityNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderSurname() {
        return holderSurname;
    }

    public void setHolderSurname(String holderSurname) {
        this.holderSurname = holderSurname;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
}
