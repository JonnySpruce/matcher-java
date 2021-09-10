package com.scottlogic.training.controllers.solace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scottlogic.training.controllers.websocket.WebsocketController;
import com.scottlogic.training.matcher.events.TradesEvent;
import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import com.solacesystems.jms.message.SolBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SolaceSpringController {
    private final Matcher matcher;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${solace.topic.trades}")
    private String tradesTopic;

    private Logger logger;

    @Autowired
    public SolaceSpringController(Matcher matcher) {
        this.matcher = matcher;
        this.logger = LoggerFactory.getLogger(SolaceSpringController.class);
    }

    @PostConstruct
    private void customizeJmsTemplate() {
        // Update the jmsTemplate's connection factory to cache the connection
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
        jmsTemplate.setConnectionFactory(ccf);

        // By default Spring Integration uses Queues, but if you set this to true you
        // will send to a PubSub+ topic destination
        jmsTemplate.setPubSubDomain(true);
    }

    @JmsListener(destination = "${solace.queue.placeOrder}")
    public void placeOrder(SolBytesMessage placeOrderMessage) throws Exception {
        String jsonOrder = new String(placeOrderMessage.getBackingArray());
        ObjectMapper objectMapper = new ObjectMapper();
        Order newOrder = objectMapper.readValue(jsonOrder, Order.class);

        matcher.receiveOrder(newOrder);

        logger.info("Solace order placed: " + jsonOrder);
    }

    @JmsListener(destination = "${solace.queue.placeOrderWildcard}")
    public void placeOrderWildcard(SolBytesMessage placeOrderMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOrder = new String(placeOrderMessage.getBackingArray());

        JsonNode jsonNode = mapper.readTree(jsonOrder);
        String account = placeOrderMessage.getJMSDestination().toString().split("/")[1];
        ((ObjectNode) jsonNode).put("account", account);

        jsonOrder = mapper.writeValueAsString(jsonNode);

        Order newOrder = mapper.readValue(jsonOrder, Order.class);

        logger.info("Solace wildcard order placed: " + newOrder);

        matcher.receiveOrder(newOrder);
    }

    @EventListener
    public void tradeEventHandler(TradesEvent tradesEvent) throws JsonProcessingException {
        List<Trade> trades = tradesEvent.getTrades();
        publishTrades(trades);
    }

    private void publishTrades(List<Trade> trades) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String tradesJSON = mapper.writeValueAsString(trades);

        logger.info("Emitting Trade over Solace Topic = " + tradesJSON);
        jmsTemplate.convertAndSend(tradesTopic, tradesJSON);
    }
}