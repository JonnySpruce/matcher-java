package com.scottlogic.training.matcher;

import com.scottlogic.training.matcher.enums.OrderAction;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Order implements Comparable<Order>, Serializable {
    private static final AtomicLong idCounter = new AtomicLong();
    private Timestamp createDateTime;
    private String id;
    private String account;
    private int quantity;
    private double price;
    private OrderAction action;

    @JsonCreator
    public Order(@JsonProperty("account") String account, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity, @JsonProperty("action") OrderAction action) {
        this.id = UUID.randomUUID().toString();
        this.account = account;
        this.price = price;
        this.quantity = quantity;
        this.action = action;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.account = "account";
        this.price = 0.0;
        this.quantity = 0;
        this.action = OrderAction.BUY;
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

    public Order(String id, String account, double price, int quantity, OrderAction action, Timestamp createDateTime) {
        this.id = id;
        this.account = account;
        this.price = price;
        this.quantity = quantity;
        this.action = action;
        this.createDateTime = createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0) throw new Exception("Quantity must be greater than or equal to 0");
        this.quantity = quantity;
    }

    public void reduceQuantity(int amount) throws Exception {
        setQuantity(quantity - amount);
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public OrderAction getAction() {
        return action;
    }

    public void setAction(OrderAction action) {
        this.action = action;
    }

    @Override
    public int compareTo(Order otherOrder) {
        int comparison = Double.compare(getPrice(), otherOrder.getPrice());

        if (comparison == 0) {
            comparison = createDateTime.compareTo(otherOrder.getCreateDateTime());
        }

        return comparison;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "createDateTime=" + createDateTime +
                ", id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Order)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Order order = (Order) o;

        // Compare the data members and return accordingly
        return  id.equals(order.id)
                && account.equals(order.account)
                && Double.compare(price, order.price) == 0
                && quantity == order.quantity
                && action.equals(order.action)
                && createDateTime.equals(order.createDateTime);
    }
}
