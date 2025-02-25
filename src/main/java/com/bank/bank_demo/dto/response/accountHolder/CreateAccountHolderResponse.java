package com.bank.bank_demo.dto.response.accountHolder;

public class CreateAccountHolderResponse {
    private long id;
    private String holderName;
    private String holderSurname;
    private String identityNumber;

    public CreateAccountHolderResponse() {
    }

    public CreateAccountHolderResponse(long id, String holderName, String holderSurname, String identityNumber) {
        this.id = id;
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.identityNumber = identityNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
