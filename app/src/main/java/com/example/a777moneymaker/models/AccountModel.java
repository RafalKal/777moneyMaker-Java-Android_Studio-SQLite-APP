package com.example.a777moneymaker.models;

public class AccountModel {

    private int id;
    private String name;
    private Boolean isMainAcc;
    private float balance;

    public AccountModel(String name, boolean isMainAcc) {
        this.name = name;
        this.isMainAcc = isMainAcc;
        this.balance = 0;
    }
    public AccountModel(int id, String name, boolean isMainAcc) {
        this.id = id;
        this.name = name;
        this.isMainAcc = isMainAcc;
        this.balance = 0;
    }
    public AccountModel(int id, String name, boolean isMainAcc, float balance) {
        this.id = id;
        this.name = name;
        this.isMainAcc = isMainAcc;
        this.balance = balance;
    }

    public AccountModel(String name, boolean isMainAcc, float balance) {
        this.name = name;
        this.isMainAcc = isMainAcc;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "id= " + id + ", nazwa= " + name + ", główne konto= " + isMainAcc + ", saldo= " + balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isMainAcc() {
        return isMainAcc;
    }

    public void setMainAcc(boolean mainAcc) {
        isMainAcc = mainAcc;
    }

    public AccountModel() {}
}
