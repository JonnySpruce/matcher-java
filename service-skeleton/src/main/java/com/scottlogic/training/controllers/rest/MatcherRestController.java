package com.scottlogic.training.controllers.rest;

import com.scottlogic.training.matcher.orderList.IOrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;

@Component
@RestController
public class MatcherRestController {
    private static Matcher matcher;

    @Autowired
    public MatcherRestController(Matcher matcher) {
        this.matcher = matcher;
    }

    @PostMapping("/orders")
    public List<Trade> placeOrder(@RequestBody Order newOrder) throws Exception {
        System.out.println("REST ORDER");
        return matcher.receiveOrder(newOrder);
    }

    @GetMapping("/orders")
    public IOrderList getOrders() throws Exception {
        return matcher.getOrderList();
    }
}
