package com.scottlogic.training.matcher.orderList;

import com.scottlogic.training.matcher.Order;
import com.scottlogic.training.matcher.enums.OrderAction;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;


public class OrderSparkList extends OrderList {
    private final SparkSession spark;
    private final Encoder<Order> orderEncoder;
    private Dataset<Order> orders;

    @Value("${parquet.root}")
    private String parquetRoot;

    @Value("${parquet.orders}")
    private String parquetOrders;

    private String parquetOrdersPath;

    @Autowired
    public OrderSparkList(SparkSession spark) {
        this.spark = spark;
        orderEncoder = Encoders.bean(Order.class);
    }

    @PostConstruct
    public void init() {
        parquetOrdersPath = parquetRoot + "/" + parquetOrders;
        try {
            orders = spark.read().load(parquetOrdersPath).as(orderEncoder);
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
//        Dataset<Order> filteredOrders = orders.filter((FilterFunction<Order>) o -> o.getAction() == action);
//        try {
//            return filteredOrders.collectAsList();
//        } catch (Exception e) {
//            System.out.println("exception = " + e);
//            return new ArrayList<>();
//        }
    }

    @PreDestroy
    public void save() {
        System.out.println("Saving data...");
        orders.write().format("delta").mode("overwrite").parquet(parquetOrdersPath);
    }
}
