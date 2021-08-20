package com.scottlogic.training.grpcController;

import com.google.protobuf.Timestamp;
import com.scottlogic.training.*;
import com.scottlogic.training.matcher.Matcher;
import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.OrderAction;
import com.scottlogic.training.matcher.Trade;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class OrderGRPCController extends MatcherGrpc.MatcherImplBase {
    private final Matcher matcher;

    @Autowired
    public OrderGRPCController(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public void placeOrder(PlaceOrderGrpc order, StreamObserver<TradeGrpc> tradeStream) {
        Order newOrder = new Order(order.getAccount(), order.getPrice(), order.getQuantity(), OrderAction.valueOf(order.getAction()));

        List<Trade> trades;
        try {
            trades = matcher.receiveOrder(newOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (Trade t : trades) {
            TradeGrpc tradeGrpc = TradeGrpc.newBuilder().setPrice(t.getPrice()).setQuantity(t.getQuantity()).build();
            tradeStream.onNext(tradeGrpc);
        }
        tradeStream.onCompleted();
    }

    @Override
    public void getOrders(NoParams np, StreamObserver<OrderGrpc> orderStream) {
        List<Order> orders = matcher.getOrderList().getAllOrders();

        for (Order o : orders) {
            Timestamp orderTimestamp = Timestamp.newBuilder()
                    .setSeconds(o.getCreateDateTime().getTime() / 1000)
                    .setNanos(o.getCreateDateTime().getNanos())
                    .build();

            OrderGrpc orderGrpc = OrderGrpc.newBuilder()
                    .setId(o.getId())
                    .setAccount(o.getAccount())
                    .setAction(o.getAction().toString())
                    .setPrice(o.getPrice())
                    .setCreateDateTime(orderTimestamp)
                    .setQuantity(o.getQuantity())
                    .build();

            orderStream.onNext(orderGrpc);
        }
        orderStream.onCompleted();
    }
}
