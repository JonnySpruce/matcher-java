package com.scottlogic.training.rest;

import com.scottlogic.training.matcher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private static Matcher matcher;

    @Autowired
    public OrderController(Matcher matcher) {
        this.matcher = matcher;
    }

    @PostMapping("/orders")
    public List<Trade> placeOrder(@RequestBody Order newOrder) throws Exception {
        return matcher.receiveOrder(newOrder);
    }

    @GetMapping("/orders")
    public IOrderList getOrders() throws Exception {
        return matcher.getOrderList();
    }
}
