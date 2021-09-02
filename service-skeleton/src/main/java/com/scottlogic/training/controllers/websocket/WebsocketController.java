package com.scottlogic.training.controllers.websocket;

import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import com.scottlogic.training.matcher.events.TradesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class WebsocketController {
    private Matcher matcher;
    private SimpMessagingTemplate webSocket;
    private Logger logger;

    @Autowired
    public WebsocketController(Matcher matcher, SimpMessagingTemplate webSocket) {
        this.logger = LoggerFactory.getLogger(WebsocketController.class);
        this.matcher = matcher;
        this.webSocket = webSocket;
    }

    @MessageMapping("/placeOrder")
    public void placeOrder(Order order) throws Exception {
        logger.info("Websocket order placed: " + order.toString());
        matcher.receiveOrder(order);
    }

    @EventListener
    public void emitTrade(TradesEvent event) throws Exception {
        logger.debug("Emitting trade event over websocket: " + event.toString());
        webSocket.convertAndSend("/topic/trades", event.getTrades());
    }
}