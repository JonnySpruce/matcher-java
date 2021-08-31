package com.scottlogic.training.config;

import com.scottlogic.training.matcher.IOrderList;
import com.scottlogic.training.matcher.OrderArrayList;
import com.scottlogic.training.matcher.OrderFluxList;
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
//
//    @Bean
//    public JCSMPSession getSolaceSession(
//            @Value("${solace.java.host}") String solaceHost,
//            @Value("${solace.java.client-username}") String solaceUsername,
//            @Value("${solace.java.client-password}") String solacePassword,
//            @Value("${solace.java.msg-vpn}") String solaceVpn
//    ) {
//        final JCSMPProperties properties = new JCSMPProperties();
//        properties.setProperty(JCSMPProperties.HOST, solaceHost);
//        properties.setProperty(JCSMPProperties.USERNAME, solaceUsername);
//        properties.setProperty(JCSMPProperties.USERNAME, solacePassword);
//        properties.setProperty(JCSMPProperties.VPN_NAME,  solaceVpn);
//        JCSMPSession session = null;
//        try {
//            session = JCSMPFactory.onlyInstance().createSession(properties);
//            session.connect();
//            System.out.println("== CONNECTED TO SOLACE INSTANCE ==");
//        } catch (JCSMPException e) {
//            System.out.println("== COULD NOT CONNECT TO SOLACE INSTANCE ==");
//            e.printStackTrace();
//        }
//        return session;
//    }

    @Primary
    @Bean
    public IOrderList getOrderList() {
        return new OrderFluxList();
    }
}