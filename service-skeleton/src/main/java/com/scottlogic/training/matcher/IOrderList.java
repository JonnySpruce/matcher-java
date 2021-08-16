package com.scottlogic.training.matcher;

import java.util.List;

public interface IOrderList {
    public List<Order> getBuyOrders();

    public List<Order> getSellOrders();

    public void addOrder(Order order) throws Exception;

    public void updateOrder(Order order) throws Exception;

    public void removeOrder(Order order) throws Exception;

    public void removeOrders(List<Order> orders) throws Exception;

    public void resetOrders();

    public List<Order> getActionOrders(Order order) throws Exception;

    public List<Order> getOppositeActionOrders(Order order) throws Exception;
}
