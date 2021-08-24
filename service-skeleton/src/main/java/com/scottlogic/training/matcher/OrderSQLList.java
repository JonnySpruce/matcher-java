package com.scottlogic.training.matcher;

import org.apache.commons.net.ntp.TimeStamp;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class OrderSQLList extends OrderList {
    private final SparkSession spark;

    @Autowired
    public OrderSQLList(SparkSession spark) {
        this.spark = spark;
    }

    public void addOrder(Order newOrder) {
        System.out.println("Adding new order: " + newOrder.toString());
        List<Order> newOrderList = new ArrayList<>();
        newOrderList.add(newOrder);
        Dataset<Order> newOrderDs = spark.createDataset(newOrderList, Encoders.bean(Order.class));
        newOrderDs.createOrReplaceTempView("ordersTemp");
        spark.sql("INSERT INTO TABLE orders SELECT * FROM ordersTemp");
    }

    public void removeOrder(Order removeOrder) {
        spark.sql("DROP TABLE IF EXISTS newOrders");
        Dataset<Row> newDF = spark.sql("CREATE TABLE newOrders AS SELECT * FROM orders WHERE id <> \"" + removeOrder.getId() + "\"");
        spark.sql("TRUNCATE TABLE orders");
        spark.table("newOrders").show();
        spark.sql("INSERT INTO TABLE orders SELECT * FROM newOrders");
        spark.sql("DROP TABLE newOrders");
    }

    public List<Order> getBuyOrders() {
        return getOrders(OrderAction.BUY);
    }

    public List<Order> getSellOrders() {
        return getOrders(OrderAction.SELL);
    }

    private List<Order> getOrders(OrderAction action) {
        System.out.println("GET ORDERS");
        Dataset<Row> orders = spark.sql("SELECT * FROM orders WHERE action = \"" + action + "\" ORDER BY price");
        List<Order> orderList = new ArrayList<>();
        List<Row> orderRowList = orders.collectAsList();
        for (Row r : orderRowList) {
            System.out.printf("\n%s, %s, %s, %s, %s, %s\n", r.get(3), r.get(0), r.get(4), r.get(5), r.get(1), r.get(2));
            Order o = new Order(
                    r.getString(3),
                    r.getString(0),
                    r.getDouble(4),
                    r.getInt(5),
                    OrderAction.valueOf(r.getString(1)),
                    r.getTimestamp(2)
            );
            orderList.add(o);
        }
        return orderList;
    }
}
