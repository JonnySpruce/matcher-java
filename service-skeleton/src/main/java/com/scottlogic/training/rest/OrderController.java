package com.scottlogic.training.rest;

import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.OrderList;
import com.scottlogic.training.matcher.Trade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @PostMapping("/orders")
    public List<Trade> placeOrder(@RequestBody Order newOrder) throws Exception {
        return Matcher.receiveOrder(newOrder);
    }

    @GetMapping("/orders")
    public OrderList getOrders() throws Exception {
        return Matcher.getOrderList();
    }
}
