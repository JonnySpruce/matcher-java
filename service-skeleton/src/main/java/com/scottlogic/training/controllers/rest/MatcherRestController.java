package com.scottlogic.training.controllers.rest;

import com.scottlogic.training.controllers.solace.SolaceSpringController;
import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import com.scottlogic.training.matcher.orderList.IOrderList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatcherRestController {
    private static Matcher matcher;
    private Logger logger;

    @Autowired
    public MatcherRestController(Matcher matcher) {
        this.matcher = matcher;
        this.logger = LoggerFactory.getLogger(MatcherRestController.class);
    }

    @PostMapping("/orders")
    public List<Trade> placeOrder(@RequestBody Order newOrder) throws Exception {
        logger.info("REST order placed: " + newOrder);
        return matcher.receiveOrder(newOrder);
    }

    @GetMapping("/orders")
    public IOrderList getOrders() throws Exception {
        return matcher.getOrderList();
    }
}
