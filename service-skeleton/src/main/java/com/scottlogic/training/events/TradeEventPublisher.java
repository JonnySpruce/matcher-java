package com.scottlogic.training.events;

import com.scottlogic.training.matcher.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TradeEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(final Trade trade) {
        System.out.println("Publishing trade event. ");
        TradeEvent tradeEvent = new TradeEvent(this, trade);
        applicationEventPublisher.publishEvent(tradeEvent);
    }

    public void publish(final List<Trade> trade) {
        for (Trade t : trade) {
            publish(t);
        }
    }
}