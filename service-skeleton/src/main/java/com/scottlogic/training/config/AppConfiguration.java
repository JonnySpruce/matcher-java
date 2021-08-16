package com.scottlogic.training.config;

import com.scottlogic.training.matcher.IOrderList;
import com.scottlogic.training.matcher.OrderSQLList;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {


    @Bean
    public SparkSession getSparkSession()
    {
        SparkConf conf = new SparkConf();
        conf.set("spark.master", "local");

        SparkSession spark = SparkSession.builder()
                .appName("orderMatcher")
                .config(conf)
                .enableHiveSupport()
                .getOrCreate();

        return spark;
    }

    @Primary
    @Bean
    public IOrderList getOrderList(SparkSession spark) {
        return new OrderSQLList(spark);
    }
}