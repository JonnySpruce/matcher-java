package com.scottlogic.training.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    OrderDTO order1;
    OrderDTO order2;


    @BeforeEach
    void setUp() {
        order1 = new OrderDTO("0001", 10, 20, false);
        order2 = new OrderDTO("0002", 10, 20, true);

    }

    @Test
    void getAccNo() {
        assertEquals("0001", order1.getAccNo());
    }

    @Test
    void getOrderType() {
        assertEquals(false, order1.getOrderType());
        assertEquals(true, order2.getOrderType());

    }

    @Test
    void getQuantity() {
        assertEquals(20, order1.getQuantity());
    }

    @Test
    void getPrice() {
        assertEquals(10, order1.getPrice());
    }
}