package com.scottlogic.training.matcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matcher {
    private static OrderList orderList;

    static {
        orderList = new OrderList();
    }

    public static OrderList getOrderList() {
        return orderList;
    }

    public static List<Trade> receiveOrder(Order order) throws Exception {
        List<Order> oppositeOrders = orderList.getOppositeActionList(order);

        List<Trade> trades = executeTrades(order, oppositeOrders);

        if (order.getQuantity() > 0)
            orderList.addOrder(order);

        return trades;
    }
    
    private static List<Trade> executeTrades(Order newOrder, List<Order> oppositeOrderList) throws Exception {
        List<Trade> trades = new ArrayList<>();
        List<Order> removeOrders = new ArrayList<>();

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
                }
            }
        }

        orderList.removeOrders(removeOrders);

        return trades;
    }

    private static boolean isOrderMatch(Order newOrder, Order oldOrder) {
        if (newOrder.getAction() == oldOrder.getAction()) return false;
        if (newOrder.getAction() == OrderAction.BUY && newOrder.getPrice() >= oldOrder.getPrice()) return true;

        return newOrder.getAction() == OrderAction.SELL && newOrder.getPrice() <= oldOrder.getPrice();
    }

}
