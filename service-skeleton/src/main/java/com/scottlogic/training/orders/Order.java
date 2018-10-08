package com.scottlogic.training.orders;

public class Order implements Comparable <Order> {

    private int orderQuantity;
    private int orderPrice;
    private String orderType;
    private String userName;

    // constructor
    public Order() {
    };

    public Order (int quantity, int price, String type, String userName) {
        this.orderQuantity = quantity;
        this.orderPrice = price;
        this.orderType = type;
        this.userName = userName;
    };

    // TODO add comment
    public int compareTo(Order other) {
        if (this.orderPrice < other.getOrderPrice()) {
            return -1;
        } else if (this.orderPrice == other.getOrderPrice()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "userName='" + userName + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }

    public void reduceOrderQuantity(int reduce) {
        this.orderQuantity-=reduce;
    }

    public void setOrderQuantity(int quantity) {
        this.orderQuantity = quantity;
    }

    public void setOrderPrice(int price) {
        this.orderPrice = price;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}