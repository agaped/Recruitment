package com.awin.recruitment.kafka.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Transaction {

    private int id;
    private LocalDate saleDate;
    private List<Product> products;

    public Transaction(int id, LocalDate saleDate, List<Product> products) {
        this.id = id;
        this.saleDate = saleDate;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
