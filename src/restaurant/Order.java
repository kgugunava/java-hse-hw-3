// src/restaurant/Order.java
package restaurant;

import java.util.UUID;
import java.util.concurrent.Future;

public class Order {
    private final String id;
    private final String customerName;
    private final String dishType;
    private final long timestamp;
    private volatile Future<Order> future;

    public Order(String customerName, String dishType) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.dishType = dishType;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getDishType() { return dishType; }
    public long getTimestamp() { return timestamp; }

    public void setFuture(Future<Order> future) { this.future = future; }
    public Future<Order> getFuture() { return future; }

    @Override
    public String toString() {
        return String.format("Order{id='%s', customer='%s', dish='%s'}", id, customerName, dishType);
    }
}