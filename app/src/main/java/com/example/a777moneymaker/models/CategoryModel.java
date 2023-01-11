package com.example.a777moneymaker.models;

public class CategoryModel {
    private int id;
    private String name;

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryModel(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return       "id= " + id +
                ", nazwa= " + name;
    }

    public CategoryModel() {
        // EMPTY CONSTUCTOR
    }
}



