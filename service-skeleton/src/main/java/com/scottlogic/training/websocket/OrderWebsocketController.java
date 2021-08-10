package com.scottlogic.training.websocket;

import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrderWebsocketController {
    @MessageMapping("/placeOrder")
    @SendTo("/topic/trades")
    public List<Trade> greeting(Order order) throws Exception {
        System.out.println("Order received!");

        return Matcher.receiveOrder(order);
    }
}