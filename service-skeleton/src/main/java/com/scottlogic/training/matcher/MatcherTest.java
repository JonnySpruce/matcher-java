package com.scottlogic.training.matcher;

import com.scottlogic.training.market.Market;
import com.scottlogic.training.orders.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatcherTest {

    Matcher matcher;
    Market BTC_GBP;
    OrderDTO order1;
    OrderDTO order2;
    OrderDTO order3;
    OrderDTO order4;
    OrderDTO order11;
    OrderDTO order12;
    OrderDTO order13;
    OrderDTO order14;
    OrderDTO order21;
    OrderDTO order22;



    @BeforeEach
    void setUp() {
        BTC_GBP = new Market("BTC_GBP");
        order1 = new OrderDTO("1", 30, 40, false);
        order2 = new OrderDTO("2", 30, 40, true);
        order3 = new OrderDTO("3", 10, 50, true);
        order4 = new OrderDTO("4", 40, 50, false);
        order11 = new OrderDTO("11", 40, 30, false);
        order12 = new OrderDTO("12", 40, 80, false);
        order13 = new OrderDTO("13", 40, 100, false);
        order14 = new OrderDTO("14", 40, 30, true);
        order21 = new OrderDTO("21", 30, 40, false);
        order22 = new OrderDTO("22", 30, 80, true);

    }

    @Test
    void addSellOrder(){
        matcher = new Matcher(BTC_GBP, order1);
        matcher.match();
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(order1);
        assertEquals(orders, BTC_GBP.getSellOrders());
    }

    @Test
    void addBuyOrder(){
        matcher = new Matcher(BTC_GBP, order2);
        matcher.match();
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(order2);
        assertEquals(orders, BTC_GBP.getBuyOrders());
    }

    @Test
    void sortByTime(){
        List<OrderDTO> orders = new ArrayList<>();



        orders.add(order11);
        orders.add(order12);
        orders.add(order13);

        matcher = new Matcher(BTC_GBP, order11);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order12);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order13);
        matcher.match();

        assertEquals(orders, BTC_GBP.getSellOrders());
    }

    @Test
    void sortAfterPerfectMatch(){
        List<OrderDTO> orders = new ArrayList<>();

        orders.add(order12);
        orders.add(order13);

        matcher = new Matcher(BTC_GBP, order11);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order12);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order13);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order14);
        matcher.match();

        assertEquals(orders, BTC_GBP.getSellOrders());

    }

    @Test
    void sortAfterIncompleteMatch(){
        List<OrderDTO> orders = new ArrayList<>();

        orders.add(order22);

        matcher = new Matcher(BTC_GBP, order21);
        matcher.match();
        matcher = new Matcher(BTC_GBP, order22);
        matcher.match();

        assertEquals(orders, BTC_GBP.getBuyOrders());
        assertEquals(0, BTC_GBP.getSellOrders().size());



    }

    @Test
    void getMarket() {

    }

    @Test
    void match(){
        matcher = new Matcher(BTC_GBP, order1);
    }
}
