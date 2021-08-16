package com.scottlogic.training.matcher;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSparkList extends OrderList {
    private final SparkSession spark;
    private final Encoder<Order> orderEncoder;
    private Dataset<Order> orders;

    @Autowired
    public OrderSparkList(SparkSession spark) {
        this.spark = spark;
        orderEncoder = Encoders.bean(Order.class);
    }

    @PostConstruct
    public void init() {
        try {
            orders = spark.table("orders").as(orderEncoder);
        } catch (Exception e) {
            e.printStackTrace();
            orders = spark.createDataset(new ArrayList<>(), orderEncoder);
        }
        orders.show();
    }

    public void addOrder(Order newOrder) {
        System.out.println("Adding new order: " + newOrder.toString());
        List<Order> newOrderList = new ArrayList<>();
        newOrderList.add(newOrder);
        Dataset<Order> newOrderDs = spark.createDataset(newOrderList, orderEncoder);
        orders = orders.union(newOrderDs);

        orders.show(false);
        System.out.println("^ union");
        save();
    }

    public void removeOrder(Order removeOrder) {
        orders = orders.filter((FilterFunction<Order>) o -> o.getId().equals(removeOrder.getId()));
        save();
    }

    public List<Order> getBuyOrders() {
        return getOrders(OrderAction.BUY);
    }

    public List<Order> getSellOrders() {
        return getOrders(OrderAction.SELL);
    }

    private List<Order> getOrders(OrderAction action) {
        System.out.println("GET ORDERS");
        orders.show();
        return spark.sql("SELECT * FROM orders WHERE action = \"" + action + "\"")
                .as(orderEncoder)
                .collectAsList();
//        return orders.filter((FilterFunction<Order>) o -> o.getAction() == action).collectAsList();
    }

    @PreDestroy
    public void save() {
        System.out.println("Saving data...");
        orders.createOrReplaceTempView("ordersTemp");

        spark.sql("INSERT OVERWRITE TABLE orders SELECT * FROM ordersTemp");
        spark.table("orders").show(false);
//      trades.write().mode("overwrite").parquet(parquetTradesPath);
    }
}
