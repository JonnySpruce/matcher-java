package com.scottlogic.training.trades;

import java.util.Date;

public class Trade {
    private double price;
    private float quantity;
    private Date time;

    public Trade(double price, float quantity) {
        this.price = price;
        this.quantity = quantity;
        this.time = new Date();
    }

    public double getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }


    public Date getTime() {
        return time;
    }

}
