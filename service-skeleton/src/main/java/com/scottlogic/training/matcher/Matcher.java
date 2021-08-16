package com.scottlogic.training.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Matcher {
    private final IOrderList orderList;

    @Autowired
    public Matcher(IOrderList orderList) {
        this.orderList = orderList;
    }

    public IOrderList getOrderList() {
        return orderList;
    }

    public List<Trade> receiveOrder(Order order) throws Exception {
        System.out.println("orderList");
        System.out.println(orderList);

        List<Order> oppositeOrders = orderList.getOppositeActionOrders(order);

        List<Trade> trades = executeTrades(order, oppositeOrders);

        if (order.getQuantity() > 0)
            orderList.addOrder(order);

        return trades;
    }
    
    private List<Trade> executeTrades(Order newOrder, List<Order> oppositeOrderList) throws Exception {
        List<Trade> trades = new ArrayList<>();
        List<Order> removeOrders = new ArrayList<>();
        List<Order> updateOrders = new ArrayList<>();

        for (Order oldOrder : oppositeOrderList) {
            if (newOrder.getQuantity() == 0) break;

            if (isOrderMatch(newOrder, oldOrder)) {
                System.out.println("ORDER MATCHED");
                Trade newTrade = Trade.generateTrade(newOrder, oldOrder);
                newOrder.reduceQuantity(newTrade.getQuantity());
                oldOrder.reduceQuantity(newTrade.getQuantity());
                trades.add(newTrade);

                if (oldOrder.getQuantity() == 0) {
                    removeOrders.add(oldOrder);
                } else {
                    updateOrders.add(oldOrder);
                }
            }
        }
        for (Order o : updateOrders){
            orderList.updateOrder(o);
        }
        orderList.removeOrders(removeOrders);

        return trades;
    }

    private boolean isOrderMatch(Order newOrder, Order oldOrder) {
        if (newOrder.getAction() == oldOrder.getAction()) return false;
        if (newOrder.getAction() == OrderAction.BUY && newOrder.getPrice() >= oldOrder.getPrice()) return true;

        return newOrder.getAction() == OrderAction.SELL && newOrder.getPrice() <= oldOrder.getPrice();
    }

}
