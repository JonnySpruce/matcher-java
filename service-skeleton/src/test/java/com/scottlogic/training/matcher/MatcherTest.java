package com.scottlogic.training.matcher;

import static org.junit.Assert.*;

import com.scottlogic.training.matcher.enums.OrderAction;
import com.scottlogic.training.matcher.orderList.IOrderList;
import com.scottlogic.training.matcher.orderList.OrderSQLList;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class MatcherTest {
    private static Matcher matcher;

    @BeforeClass
    public static void setUp() {
        SparkConf conf = new SparkConf();
        conf.set("spark.master", "local");

        SparkSession spark = SparkSession.builder()
                .appName("testOrderMatcher")
                .config(conf)
                .enableHiveSupport()
                .getOrCreate();
        IOrderList list = new OrderSQLList(spark);
        matcher = new Matcher(list);
        matcher.getOrderList().resetOrders();
    }

    @After
    public void tidyUp() {
        matcher.getOrderList().resetOrders();
    }

    @Test
    public void addsBuyOrder() throws Exception {
        Order order = new Order("test", 2.0, 1, OrderAction.BUY);
        matcher.receiveOrder(order);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

        assertEquals(0, sellList.size());
        assertEquals(1, buyList.size());
        assertEquals(order, buyList.get(0));
    }

    @Test
    public void addsSellOrder() throws Exception {
        Order order = new Order("test", 2.0, 1, OrderAction.SELL);
        matcher.receiveOrder(order);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

        assertEquals(0, buyList.size());
        assertEquals(1, sellList.size());
        assertEquals(order, sellList.get(0));
    }

    @Test
    public void singleBuyOrderExactMatch() throws Exception {
        Order buyOrder = new Order("test", 2.0, 1, OrderAction.BUY);
        Order sellOrder = new Order("test2", 2.0, 1, OrderAction.SELL);

        matcher.receiveOrder(buyOrder);
        List<Trade> trades = matcher.receiveOrder(sellOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

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

        matcher.receiveOrder(sellOrder);
        List<Trade> trades = matcher.receiveOrder(buyOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

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

        matcher.receiveOrder(sellOrder);
        List<Trade> trades = matcher.receiveOrder(buyOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

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

        matcher.receiveOrder(sellOrder);
        List<Trade> trades = matcher.receiveOrder(buyOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

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
            matcher.receiveOrder(sellOrder);
        }

        Order buyOrder = new Order("test2", 2.0, 11, OrderAction.BUY);
        List<Trade> trades = matcher.receiveOrder(buyOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

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

        matcher.receiveOrder(sellOrder);
        matcher.receiveOrder(sellOrder2);
        List<Trade> trades = matcher.receiveOrder(buyOrder);

        List<Order> buyList = matcher.getOrderList().getBuyOrders();
        List<Order> sellList = matcher.getOrderList().getSellOrders();

        assertEquals(0, buyList.size());
        assertEquals(1, sellList.size());
        assertEquals(1, trades.size());

        assertEquals(1.0, trades.get(0).getPrice(), 0.0);
        assertEquals(1, trades.get(0).getQuantity());

        assertEquals(2.0, sellList.get(0).getPrice(), 0.0);
    }
}