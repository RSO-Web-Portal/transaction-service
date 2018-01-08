package com.kumuluz.ee.transaction;


import java.util.Date;

public class Transaction {

    private String id;
    private String productId;
    private String accountId;
    private Date executionDate;


    public Transaction() {

    }


    public Transaction(String id, String productId, String accountId, Date executionDate) {
        this.id = id;
        this.productId = productId;
        this.accountId = accountId;
        this.executionDate = executionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }
}