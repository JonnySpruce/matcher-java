package com.scottlogic.training.config;

import com.scottlogic.training.matcher.orderList.IOrderList;
import com.scottlogic.training.matcher.orderList.OrderArrayList;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {
//    @Bean
//    public SparkSession getSparkSession()
//    {
//        SparkConf conf = new SparkConf();
//        conf.setMaster("spark://WS01215.localdomain:7077");
//
//        SparkSession spark = SparkSession.builder()
//                .appName("orderMatcher")
//                .config(conf)
//                .enableHiveSupport()
//                .getOrCreate();
//
//        return spark;
//    }

//    @Primary
//    @Bean
//    public IOrderList getOrderList(SparkSession sparkSession) {
//        return new OrderSQLList(sparkSession);
//    }

    @Primary
    @Bean
    public IOrderList getOrderList() {
        return new OrderArrayList();
    }
}