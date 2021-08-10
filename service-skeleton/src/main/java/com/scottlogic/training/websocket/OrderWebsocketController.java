package com.scottlogic.training.matcher;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrderWebsocketController {
    @MessageMapping("/placeOrder")
    @SendTo("/topic/trades")
    public List<Trade> greeting(Order order) throws Exception {
        return Matcher.receiveOrder(order);
    }
}