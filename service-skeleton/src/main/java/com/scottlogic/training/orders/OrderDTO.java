package com.scottlogic.training.orders;

import java.util.Comparator;
import java.util.Date;

/*
 *  This is a Data transfer object which carries the message from client to server and vice-versa.
 *  It's used by the OrderModule to serialize and deserialize messages from and for the client.
 *
 *  Add/remove fields (and types) from here as necessary in order to match your message format.
 */
public class OrderDTO implements Comparable<OrderDTO> {

    private String accNo;
    private Boolean orderType;
    private float quantity;
    private float price;
    private float quantityRemaining;
    private Date time;


    public OrderDTO() {
    }

    public OrderDTO(String accNo,  float price, float quantity, Boolean orderType) {
        super();
        this.accNo = accNo;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        this.quantityRemaining = quantity;
        this.time = new Date();

    }

    public String getAccNo() {
        return accNo;
    }

    public Boolean getOrderType() {
        return orderType;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(float i) {
        if(i == 0){
            quantityRemaining = 0;
        }
        quantityRemaining -= i;
    }

    public float getPrice() {
        return price;
    }

    public Date getTime() {
        return time;
    }

    public int compareTo(OrderDTO order) {
        if(!order.getOrderType()) {
            if (this.getPrice() < order.getPrice()) return -1;
            if (this.getPrice() > order.getPrice()) return 1;
            if (this.getTime().before(order.getTime())) return 1;
            if (this.getTime().after(order.getTime())) return -1;
            return 0;
        } else {
            if (this.getPrice() < order.getPrice()) return 1;
            if (this.getPrice() > order.getPrice()) return -1;
            if (this.getTime().before(order.getTime())) return -1;
            if (this.getTime().after(order.getTime())) return 1;
            return 0;
        }
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "accNo='" + accNo + '\'' +
                ", orderType='" + orderType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", quantityRemaining='" + quantityRemaining + '\'' +

                '}';
    }
}
