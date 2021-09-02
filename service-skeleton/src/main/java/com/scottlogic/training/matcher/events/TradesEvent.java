package com.scottlogic.training.matcher.events;

import com.scottlogic.training.matcher.Trade;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class TradesEvent extends ApplicationEvent {
    private List<Trade> trades;

    public TradesEvent(Object source, List<Trade> trades) {
        super(source);
        this.trades = trades;
    }
    public List<Trade> getTrades() {
        return trades;
    }
}