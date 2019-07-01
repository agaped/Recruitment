package com.awin.recruitment.kafka.model;

import java.math.BigDecimal;

public class SummarizedTransaction extends Transaction{

    private BigDecimal total;


    private SummarizedTransaction(Transaction t, BigDecimal total) {
        super(t.getId(), t.getSaleDate(), t.getProducts());
        this.total = total;
    }

    public static SummarizedTransaction sumTransaction(Transaction t) {
        return new SummarizedTransaction(t, countTotal(t));
    }

    private static BigDecimal countTotal(Transaction t) {
        return t.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            SummarizedTransaction other=(SummarizedTransaction) obj;
            return this.total.equals(other.total);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 7*this.total.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() +" total: "+total;
    }
}
