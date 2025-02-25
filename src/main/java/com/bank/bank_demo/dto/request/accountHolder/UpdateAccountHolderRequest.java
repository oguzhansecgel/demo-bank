package com.bank.bank_demo.dto.request.accountHolder;

public class UpdateAccountHolderRequest {
    private String holderName;
    private String holderSurname;
    private String identityNumber;

    public UpdateAccountHolderRequest() {
    }

    public UpdateAccountHolderRequest(String holderName, String holderSurname, String identityNumber) {
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
