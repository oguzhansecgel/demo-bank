package com.bank.bank_demo.dto.response.bank;

public class UpdateBankResponse {
    private long id;
    private String name;
    private String code;

    public UpdateBankResponse(long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public UpdateBankResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
