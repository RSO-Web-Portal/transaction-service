package com.kumuluz.ee.transaction;



public class Product {

    private String id;
    private String orderId;
    private String priorityId;
    private float price;

    private boolean active;

    public Product() {

    }


    public Product(String id, String orderId, String priorityId, float price) {
        this.id = id;
        this.orderId = orderId;
        this.priorityId = priorityId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}