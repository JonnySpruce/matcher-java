package com.scottlogic.training.matcher;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MatcherTest {
    @After
    public void tidyUp() {
        Matcher.getOrderList().resetOrders();
    }

    @Test
    public void addsBuyOrder() throws Exception {
        Order order = new Order("test", 2.0, 1, OrderAction.BUY);
        Matcher.receiveOrder(order);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, sellList.size());
        assertEquals(1, buyList.size());
        assertEquals(order, buyList.get(0));
    }

    @Test
    public void addsSellOrder() throws Exception {
        Order order = new Order("test", 2.0, 1, OrderAction.SELL);
        Matcher.receiveOrder(order);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, buyList.size());
        assertEquals(1, sellList.size());
        assertEquals(order, sellList.get(0));
    }

    @Test
    public void singleBuyOrderExactMatch() throws Exception {
        Order buyOrder = new Order("test", 2.0, 1, OrderAction.BUY);
        Order sellOrder = new Order("test2", 2.0, 1, OrderAction.SELL);

        Matcher.receiveOrder(buyOrder);
        List<Trade> trades = Matcher.receiveOrder(sellOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, buyList.size());
        assertEquals(0, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(2.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());
    }

    @Test
    public void singleSellOrderExactMatch() throws Exception {
        Order sellOrder = new Order("test", 2.0, 1, OrderAction.SELL);
        Order buyOrder = new Order("test2", 2.0, 1, OrderAction.BUY);

        Matcher.receiveOrder(sellOrder);
        List<Trade> trades = Matcher.receiveOrder(buyOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, buyList.size());
        assertEquals(0, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(2.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());
    }

    @Test
    public void oldOrderPartialMatch() throws Exception {
        Order sellOrder = new Order("test", 2.0, 2, OrderAction.SELL);
        Order buyOrder = new Order("test2", 2.0, 1, OrderAction.BUY);

        Matcher.receiveOrder(sellOrder);
        List<Trade> trades = Matcher.receiveOrder(buyOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, buyList.size());
        assertEquals(1, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(2.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());

        assertEquals(1, sellList.get(0).getQuantity());
    }

    @Test
    public void newOrderPartialMatch() throws Exception {
        Order sellOrder = new Order("test", 2.0, 1, OrderAction.SELL);
        Order buyOrder = new Order("test2", 2.0, 2, OrderAction.BUY);

        Matcher.receiveOrder(sellOrder);
        List<Trade> trades = Matcher.receiveOrder(buyOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(1, buyList.size());
        assertEquals(0, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(2.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());

        assertEquals(1, buyList.get(0).getQuantity());
    }

    @Test
    public void largeNewOrderMultipleMatches() throws Exception {
        for (int i = 0; i < 10; i++) {
            Order sellOrder = new Order("test", 2.0, 1, OrderAction.SELL);
            Matcher.receiveOrder(sellOrder);
        }

        Order buyOrder = new Order("test2", 2.0, 11, OrderAction.BUY);
        List<Trade> trades = Matcher.receiveOrder(buyOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(1, buyList.size());
        assertEquals(0, sellList.size());
        assertEquals(10, trades.size());

        assertEquals(2.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());

        assertEquals(1, buyList.get(0).getQuantity());
    }

    @Test
    public void correctlyMatchLowerPriceFirst() throws Exception {
        Order sellOrder = new Order("test", 2.0, 1, OrderAction.SELL);
        Order sellOrder2 = new Order("test", 1.0, 1, OrderAction.SELL);
        Order buyOrder = new Order("test2", 2.0, 1, OrderAction.BUY);

        Matcher.receiveOrder(sellOrder);
        Matcher.receiveOrder(sellOrder2);
        List<Trade> trades = Matcher.receiveOrder(buyOrder);

        List<Order> buyList = Matcher.getOrderList().getBuyList();
        List<Order> sellList = Matcher.getOrderList().getSellList();

        assertEquals(0, buyList.size());
        assertEquals(1, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(1.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());

        assertEquals(1, buyList.get(0).getQuantity());
    }
}