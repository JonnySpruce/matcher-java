package com.scottlogic.training.matcher;

import org.graalvm.compiler.lir.CompositeValue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class Order implements Comparable<Order> {
    private final String account;
    private final double price;
    private int quantity;
    private final OrderAction action;
    private final LocalDateTime createDateTime;

    public Order(String account, double price, int quantity, OrderAction action) {
        this.account = account;
        this.price = price;
        this.quantity = quantity;
        this.action = action;
        this.createDateTime = LocalDateTime.now();
    }

    public String getAccount() {
        return account;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int amount) throws Exception {
        setQuantity(quantity - amount);
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0) throw new Exception("Quantity must be greater than or equal to 0");
        this.quantity = quantity;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public OrderAction getAction() {
        return action;
    }

    @Override
    public int compareTo(Order otherOrder) {
        int comparison = Double.compare(getPrice(),  otherOrder.getPrice());

        if (comparison == 0) {
            comparison = createDateTime.compareTo(otherOrder.getCreateDateTime());
        }

        return comparison;
    }
}
