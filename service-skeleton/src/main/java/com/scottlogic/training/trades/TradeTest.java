package com.scottlogic.training.trades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeTest {
    Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade(20, 10);
    }

    @Test
    void getPrice() {
        assertEquals(20, trade.getPrice());
    }

    @Test
    void getQuantity() {
        assertEquals(10, trade.getQuantity());
    }

    @Test
    void getTime() {
        System.out.println(trade.getTime());
    }

}


