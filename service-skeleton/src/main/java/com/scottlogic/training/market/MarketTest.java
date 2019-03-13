package com.scottlogic.training.market;

import com.scottlogic.training.orders.OrderDTO;
import com.scottlogic.training.trades.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketTest {

    Market BTC_GBP;

    OrderDTO order1;
    OrderDTO order2;
    OrderDTO order3;
    OrderDTO order4;



    @BeforeEach
    void setUp() {
        BTC_GBP = new Market("BTC_GBP");

        order1 = new OrderDTO("0001",10, 20, false);
        order2 = new OrderDTO("0002", 10, 20, true);
        order3 = new OrderDTO("0003", 25, 20, false);
        order4 = new OrderDTO("0004", 25, 20, true);

        BTC_GBP.addOrder(order1);
        BTC_GBP.addOrder(order2);
        BTC_GBP.addOrder(order3);
        BTC_GBP.addOrder(order4);


    }

    @Test
    void getCurrency() {
        assertEquals(BTC_GBP.getCurrency(), "BTC_GBP");
    }

    @Test
    void getSellOrders() {
        List<OrderDTO> sell = new ArrayList<OrderDTO>();
        sell.add(order1);
        sell.add(order3);
        assertEquals(BTC_GBP.getSellOrders(), sell);

    }

    @Test
    void getBuyOrders() {
        List<OrderDTO> buy = new ArrayList<>();
        buy.add(order2);
        buy.add(order4);
        assertEquals(BTC_GBP.getBuyOrders(), buy);
    }

    @Test
    void removeOrder(){
        List<OrderDTO> sell = new ArrayList<>();
        sell.add(order1);
        BTC_GBP.removeOrder(order3);

        assertEquals(sell, BTC_GBP.getSellOrders());
    }


    @Test
    void getTrades() {
        List<Trade> trade = new ArrayList<Trade>();
        Trade trade1 = new Trade(10, 20);
        trade.add(trade1);
        BTC_GBP.addTrade(trade1);

//        trade.add(order2);
//        trade.add(order4);
        assertEquals(BTC_GBP.getTrades(), trade);
    }

    @Test
    void sortBuyOrders(){
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(order4);
        orders.add(order2);
        assertEquals(BTC_GBP.getBuyOrders(), orders);
    }

    @Test
    void sortSellOrders(){
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order3);
        assertEquals(orders, BTC_GBP.getSellOrders());
    }

}
