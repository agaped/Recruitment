package com.awin.recruitment.kafka.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Transaction {

    private long id;
    private LocalDate saleDate;
    private List<Product> products;

    public Transaction(long id, LocalDate saleDate, List<Product> products) {
        this.id = id;
        this.saleDate = saleDate;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (obj instanceof Transaction) {
            Transaction other=(Transaction) obj;
            return this.id==other.id &&
                    this.saleDate.equals(other.saleDate) &&
                    this.products.equals(other.products);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "\n{Transaction id: "+id+", saleDate: "+saleDate+", products: "+products+"}";
    }
}
