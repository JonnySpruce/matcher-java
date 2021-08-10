//package com.scottlogic.training.orders;
//
///*
// *  This is a Data transfer object which carries the message from client to server and vice-versa.
// *  It's used by the OrderModule to serialize and deserialize messages from and for the client.
// *
// *  Add/remove fields (and types) from here as necessary in order to match your message format.
// */
//public class OrderDTO {
//
//    private String userName;
//    private String orderType;
//
//    public OrderDTO() {
//    }
//
//    public OrderDTO(String userName, String orderType) {
//        super();
//        this.userName = userName;
//        this.orderType = orderType;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getOrderType() {
//        return orderType;
//    }
//    public void setOrderType(String orderType) {
//        this.orderType = orderType;
//    }
//
//    @Override
//    public String toString() {
//        return "OrderDTO{" +
//                "userName='" + userName + '\'' +
//                ", orderType='" + orderType + '\'' +
//                '}';
//    }
//}
