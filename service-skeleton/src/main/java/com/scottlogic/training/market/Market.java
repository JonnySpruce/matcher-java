package com.scottlogic.training.market;
import com.scottlogic.training.orders.OrderDTO;
import com.scottlogic.training.trades.Trade;
import org.junit.jupiter.api.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Market {
    private String currency;
    private List<OrderDTO> sellOrders;
    private List<OrderDTO> buyOrders;
    private List<Trade> trades;

    public Market(String currency) {
        this.currency = currency;
        this.sellOrders = new ArrayList<OrderDTO>();
        this.buyOrders = new ArrayList<OrderDTO>();
        this.trades = new ArrayList<Trade>();
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderDTO> getSellOrders() {
        return sellOrders;
    }

    public OrderDTO getSellOrders(int i) {
        return sellOrders.get(i);
    }


    public void addOrder(OrderDTO order) {
        if(!order.getOrderType()){
            sellOrders.add(order);
            Collections.sort(sellOrders);
        } else {
            buyOrders.add(order);
            Collections.sort(buyOrders);
        }
    }
    public void removeOrder(OrderDTO order) {
        if(!order.getOrderType()){
            sellOrders.remove(order);
        } else {
            buyOrders.remove(order);
        }
    }
    public void addTrade(Trade trade){
        trades.add(trade);
    }

    public List<OrderDTO> getBuyOrders() {
        return buyOrders;
    }
    public OrderDTO getBuyOrders(int i) {
        return buyOrders.get(i);
    }




    public List<Trade> getTrades() {
        return trades;
    }

}

