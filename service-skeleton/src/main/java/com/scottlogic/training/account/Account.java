package com.scottlogic.training.account;

public class Account {
    private String accNumber;
    private String name;

    public Account(String accNumber, String name) {
        this.accNumber = accNumber;
        this.name = name;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}