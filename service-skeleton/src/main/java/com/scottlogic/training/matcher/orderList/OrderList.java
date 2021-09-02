package com.scottlogic.training.matcher.orderList;

import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.enums.OrderAction;

import java.util.List;

public abstract class OrderList implements IOrderList {

    public abstract List<Order> getBuyOrders();

    public abstract List<Order> getSellOrders();

    public List<Order> getAllOrders() {
        List<Order> orders = getBuyOrders();
        orders.addAll(getSellOrders());
        return orders;
    };

    public abstract void addOrder(Order order) throws Exception;

    public abstract void removeOrder(Order order) throws Exception;

    public void updateOrder(Order order) throws Exception {
        removeOrder(order);
        addOrder(order);
    }

    public void removeOrders(List<Order> orders) throws Exception {
        for(Order o : orders) removeOrder(o);
    }

    public void resetOrders() {
        try {
            removeOrders(getBuyOrders());
            removeOrders(getSellOrders());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> getActionOrders(Order order) throws Exception {
        if (order.getAction() == OrderAction.BUY) {
            return getBuyOrders();
        } else if (order.getAction() == OrderAction.SELL) {
            return getSellOrders();
        } else {
            throw new Exception("Order action not recognised");
        }
    }

    public List<Order> getOppositeActionOrders(Order order) throws Exception {
        if (order.getAction() == OrderAction.BUY) {
            return getSellOrders();
        } else if (order.getAction() == OrderAction.SELL) {
            return getBuyOrders();
        } else {
            throw new Exception("Order action not recognised");
        }
    }
}
