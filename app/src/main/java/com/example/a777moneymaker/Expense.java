package com.example.a777moneymaker;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Expense {
    private String name;
    private float price;
    private String category;
    //private Date date;

    public Expense(String name, float price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        //this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Expense)) return false;
//        Expense expense = (Expense) o;
//        return Float.compare(expense.getPrice(), getPrice()) == 0 && getName().equals(expense.getName()) && getCategory().equals(expense.getCategory()) && getDate().equals(expense.getDate());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getName(), getPrice(), getCategory(), getDate());
//    }
}
