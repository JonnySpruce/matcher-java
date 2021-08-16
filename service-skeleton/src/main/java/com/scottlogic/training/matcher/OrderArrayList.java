package com.scottlogic.training.matcher;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderArrayList extends OrderList {
    private final List<Order> buyList;
    private final List<Order> sellList;

    public OrderArrayList() {
        buyList = new ArrayList<>();
        sellList = new ArrayList<>();
    }

    public List<Order> getBuyOrders() {
        return buyList;
    }

    public List<Order> getSellOrders() {
        return sellList;
    }

    public void addOrder(Order order) throws Exception {
        List<Order> addToList = getActionOrders(order);
        addToList.add(order);
        Collections.sort(addToList);
    }

    public void removeOrder(Order order) throws Exception {
        List<Order> removeFromList = getActionOrders(order);
        removeFromList.remove(order);
    }
}
