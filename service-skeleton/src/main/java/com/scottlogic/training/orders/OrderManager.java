package com.scottlogic.training.orders;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    public static final String BUY = "BUY";
    public static final String SELL = "SELL";

    private List<Order> buyOrders;
    private List<Order> sellOrders;

    // constructor
    public OrderManager() {
        this.buyOrders = new ArrayList<Order>();
        this.sellOrders = new ArrayList<Order>();
    };

    // Determines order type and adds to relevant list
    public void addOrder(Order order) {
        switch (order.getOrderType()) {
            case BUY: this.buyOrders.add(order);
                break;
            case SELL: sellOrders.add(order);
                break;
        }
    }

    // Removes an order from the relevant list
    public void removeOrder(Order order) {
        switch (order.getOrderType()) {
            case BUY: buyOrders.remove(order);
                break;
            case SELL: sellOrders.remove(order);
                break;
        }
    }

    public List<Order> getBuyOrders() {
        return buyOrders;
    }

    public List<Order> getSellOrders() {
        return sellOrders;
    }

}
