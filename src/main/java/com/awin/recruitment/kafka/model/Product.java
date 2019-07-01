package com.awin.recruitment.kafka.model;

import java.math.BigDecimal;

public class Product {

    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (obj instanceof Product) {
            Product otherProduct=(Product) obj;
            return this.name.equals(otherProduct.name) &&
                    this.price.equals(otherProduct.price);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int value=31;
        value=value+17*name.hashCode();
        value=value+31*price.hashCode();
        return value;
    }

    @Override
    public String toString() {
        return "\n{Name: "+name+", price: "+price.doubleValue()+"}";
    }
}
