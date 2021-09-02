package com.scottlogic.training.controllers.solace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scottlogic.training.matcher.events.TradesEvent;
import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import com.solacesystems.jms.message.SolBytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

//@Component
//public class SolaceController {
//    // A JCSMP Factory for the auto selected Solace PubSub+ service,
//    // This is used to create JCSMPSession(s)
//    // This is the only required bean to run this application.
//    private final SpringJCSMPFactory solaceFactory;
//    private JCSMPSession session;
//
//    @Autowired
//    public SolaceController(
//            SpringJCSMPFactory solaceFactory,
//            JCSMPProperties properties,
//            @Value("${solace.topic.placeOrder}") String orderTopicName,
//            @Value("${solace.topic.trades}") String tradeTopicName
//    ) {
//        this.solaceFactory = solaceFactory;
//
//        try {
//            session = solaceFactory.createSession();
//            session.connect();
//        } catch (JCSMPException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("subscribing to topic: " + orderTopicName);
//
//        Topic placeOrderTopic = JCSMPFactory.onlyInstance().createTopic(orderTopicName);
//        Topic tradesTopic = JCSMPFactory.onlyInstance().createTopic(tradeTopicName);
//
//        try {
//            session.addSubscription(placeOrderTopic);
//        } catch (JCSMPException e) {
//            e.printStackTrace();
//        }
//
//        session.
//
//    }
//}

@Component
public class SolaceController {
    private Matcher matcher;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${solace.topic.trades}")
    private String tradesTopic;

    @Autowired
    public SolaceController(Matcher matcher) {
        this.matcher = matcher;
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

        List<Trade> trades = matcher.receiveOrder(newOrder);

        String tradesJSON = objectMapper.writeValueAsString(trades);
        System.out.println(tradesJSON);

        jmsTemplate.convertAndSend(tradesTopic, tradesJSON);
    }

    @JmsListener(destination = "${solace.queue.placeOrderWildcard}")
    public void placeOrderWildcard(SolBytesMessage placeOrderMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String userName = placeOrderMessage.getJMSDestination().toString().split("/")[1];

        String jsonOrder = new String(placeOrderMessage.getBackingArray());

        JsonNode jsonNode = mapper.readTree(jsonOrder);

        String json = mapper.writeValueAsString(jsonNode);

        Order newOrder = mapper.readValue(jsonOrder, Order.class);

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
        System.out.println(tradesJSON);

        jmsTemplate.convertAndSend(tradesTopic, tradesJSON);
    }
}