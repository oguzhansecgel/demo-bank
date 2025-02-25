package com.bank.bank_demo.dto.request.bank;

import jakarta.validation.constraints.NotBlank;

public class UpdateBankRequest {
    @NotBlank(message = "bank name is not blank")
    private String name;
    @NotBlank(message = "bank code is not blank")
    private String code;
    public UpdateBankRequest() {
    }

    public UpdateBankRequest(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
