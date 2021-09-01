package com.scottlogic.training.events;

import com.scottlogic.training.matcher.Trade;
import org.springframework.context.ApplicationEvent;

public class TradeEvent extends ApplicationEvent {
    private Trade trade;

    public TradeEvent(Object source, Trade trade) {
        super(source);
        this.trade = trade;
    }
    public Trade getTrade() {
        return trade;
    }
}