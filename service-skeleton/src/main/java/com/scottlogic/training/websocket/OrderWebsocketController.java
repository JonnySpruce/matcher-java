package com.scottlogic.training.websocket;

import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrderWebsocketController {
    private Matcher matcher;

    @Autowired
    public OrderWebsocketController(Matcher matcher) {
        this.matcher = matcher;
    }

    @MessageMapping("/placeOrder")
    @SendTo("/topic/trades")
    public List<Trade> greeting(Order order) throws Exception {
        return matcher.receiveOrder(order);
    }
}