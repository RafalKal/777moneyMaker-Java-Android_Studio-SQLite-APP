package com.example.a777moneymaker.models;

public class AccountModel {

    private int id;
    private String name;
    private Boolean isMainAcc;

    public AccountModel(int id, String name, boolean isMainAcc) {
        this.id = id;
        this.name = name;
        this.isMainAcc = isMainAcc;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isMainAcc=" + isMainAcc +
                '}';
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
