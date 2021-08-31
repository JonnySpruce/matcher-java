package com.scottlogic.training.matcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class OrderFluxList extends OrderList {
    private Flux<Order> orders;

    public OrderFluxList() {
        orders = Flux.empty();
    }

    @Override
    public List<Order> getBuyOrders() {
        return orders
                .filter((Order o) -> o.getAction() == OrderAction.BUY)
                .collectList()
                .block();
    }

    @Override
    public List<Order> getSellOrders() {
        return orders
                .filter((Order o) -> o.getAction() == OrderAction.SELL)
                .collectList()
                .block();
    }

    @Override
    public void addOrder(Order order) throws Exception {
        orders = orders.concatWithValues(order);
    }

    @Override
    public void removeOrder(Order order) throws Exception {
        orders = orders.filter((Order o ) -> o.getId().equals(order.getId()));
    }
}
