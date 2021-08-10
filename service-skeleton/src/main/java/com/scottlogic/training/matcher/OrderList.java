package com.scottlogic.training.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderList {
    private List<Order> buyList;
    private List<Order> sellList;

    public OrderList() {
        buyList = new ArrayList<Order>();
        sellList = new ArrayList<Order>();
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

    public void removeOrders(List<Order> orders) throws Exception {
        for(Order o : orders) removeOrder(o);
    }

    public void resetOrders() {
        buyList = new ArrayList<Order>();
        sellList = new ArrayList<Order>();
    }

    public List<Order> getActionList(Order order) throws Exception {
        if (order.getAction() == OrderAction.BUY) {
            return buyList;
        } else if (order.getAction() == OrderAction.SELL) {
            return sellList;
        } else {
            throw new Exception("Order action not recognised");
        }
    }

    public List<Order> getOppositeActionList(Order order) throws Exception {
        if (order.getAction() == OrderAction.BUY) {
            return sellList;
        } else if (order.getAction() == OrderAction.SELL) {
            return buyList;
        } else {
            throw new Exception("Order action not recognised");
        }
    }
}
