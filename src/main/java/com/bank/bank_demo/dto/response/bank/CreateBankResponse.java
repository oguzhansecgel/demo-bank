package com.bank.bank_demo.dto.response.bank;

public class CreateBankResponse {
    private long id;
    private String name;
    private String code;

    public CreateBankResponse(long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public CreateBankResponse() {
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
