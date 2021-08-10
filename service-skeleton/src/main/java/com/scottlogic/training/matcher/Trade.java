package com.scottlogic.training.matcher;

import org.springframework.stereotype.Component;

public class Trade {
    private final double price;
    private final int quantity;

    private Trade(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Trade generateTrade(Order o1, Order o2) {
        Order oldOrder;
        Order newOrder;

        if (o1.getCreateDateTime().compareTo(o2.getCreateDateTime()) > 0) {
            oldOrder = o1;
            newOrder = o2;
        } else {
            oldOrder = o2;
            newOrder = o1;
        }

        int tradeQuantity = Math.min(oldOrder.getQuantity(), newOrder.getQuantity());

        return new Trade(oldOrder.getPrice(), tradeQuantity);
    }
}
