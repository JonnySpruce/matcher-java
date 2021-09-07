package com.scottlogic.training.matcher.events;

import com.scottlogic.training.matcher.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TradeEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(final List<Trade> trades) {
        if(applicationEventPublisher != null) {
            System.out.println("Publishing trades event. ");
            TradesEvent tradesEvent = new TradesEvent(this, trades);
            applicationEventPublisher.publishEvent(tradesEvent);
        } else {
            System.out.println("Warning: No event publisher defined");
        }
    }
}