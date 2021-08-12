package com.scottlogic.training.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderList extends AbstractOrderList {
    private final List<Order> buyList;
    private final List<Order> sellList;

    public OrderList() {
        buyList = new ArrayList<>();
        sellList = new ArrayList<>();
    }

    public List<Order> getBuyList() {
        return buyList;
    }

    public List<Order> getSellList() {
        return sellList;
    }

    public void addOrder(Order order) throws Exception {
        List<Order> addToList = getActionList(order);
        addToList.add(order);
        Collections.sort(addToList);
    }

    public void removeOrder(Order order) throws Exception {
        List<Order> removeFromList = getActionList(order);
        removeFromList.remove(order);
    }
}
