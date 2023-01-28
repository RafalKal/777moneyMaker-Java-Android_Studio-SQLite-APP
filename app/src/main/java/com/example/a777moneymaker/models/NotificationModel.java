package com.example.a777moneymaker.models;

public class NotificationModel {
    private int id;
    private int state;
    private float lowerLimit;

    public NotificationModel(int id, int state, float lowerLimit) {
        this.id = id;
        this.state = state;
        this.lowerLimit = lowerLimit;
    }

    public NotificationModel(int state, float lowerLimit) {
        this.state = state;
        this.lowerLimit = lowerLimit;
    }

    public NotificationModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(float lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
}
