package com.awin.recruitment.kafka.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Transaction {

    private int id;
    private LocalDate saleDate;
    private double price;
    private List<Product> products;

    public Transaction(int id) {
        this.id = id;
    }

    public Transaction(int id, LocalDate saleDate, double price, List<Product> products) {
        this.id = id;
        this.saleDate = saleDate;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
