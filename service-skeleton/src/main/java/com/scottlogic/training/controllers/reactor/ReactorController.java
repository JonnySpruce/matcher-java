package com.scottlogic.training.controllers.reactor;

import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("/reactor")
public class ReactorController {
    private Matcher matcher;
    private Scheduler scheduler;

    @Autowired
    public ReactorController(Matcher matcher) {
        this.scheduler = Schedulers.newBoundedElastic(5,10, "orderGroup");
        this.matcher = matcher;
    }

    @GetMapping("/orders")
    public Flux<Order> getOrders() {
        List<Order> orders = matcher.getOrderList().getAllOrders();
        return Flux.fromIterable(orders);
    }

    @PostMapping("/placeOrder")
    public Flux<Trade> placeOrder(@RequestBody Order newOrder) {
        return Mono.just(newOrder)
                .publishOn(scheduler)
                .map((Order o) -> {
                    try {
                        return matcher.receiveOrder(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ArrayList<Trade>();
                    }
                })
                .flatMapMany(Flux::fromIterable);
    }


}
