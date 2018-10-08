package com.scottlogic.training.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderManagerTest {

    @BeforeEach
    public void setup() {

    }

    @Test
    public void addingBuyOrderShouldIncreaseSavedBuyOrders() {
        System.out.println("test running");
        OrderManager testOrderManager = new OrderManager();
        Order testBuyOrder = new Order(10, 11, "BUY", "John Doe");
        testOrderManager.addOrder(testBuyOrder);
        assertEquals(1, testOrderManager.getBuyOrders().size(), "There must be 1 buy order1");
    }
}