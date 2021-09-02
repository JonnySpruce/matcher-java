package com.scottlogic.training.config;

import com.scottlogic.training.matcher.orderList.IOrderList;
import com.scottlogic.training.matcher.orderList.OrderArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {


//    @Bean
//    public SparkSession getSparkSession()
//    {
//        SparkConf conf = new SparkConf();
//        conf.set("spark.master", "local");
//
//        SparkSession spark = SparkSession.builder()
//                .appName("orderMatcher")
//                .config(conf)
//                .enableHiveSupport()
//                .getOrCreate();
//
//        return spark;
//    }

    @Primary
    @Bean
    public IOrderList getOrderList() {
        return new OrderArrayList();
    }
}