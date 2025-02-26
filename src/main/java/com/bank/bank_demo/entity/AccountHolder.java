package com.bank.bank_demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "account-holder")
public class AccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String holderName;
    private String holderSurname;
    @Column(unique = true)
    private String identityNumber;
    @OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public AccountHolder() {
    }

    public AccountHolder(long id, String holderName, String holderSurname, String identityNumber, List<Account> accounts) {
        this.id = id;
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.identityNumber = identityNumber;
        this.accounts = accounts;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
