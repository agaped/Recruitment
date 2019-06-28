package com.awin.recruitment.kafka.model;

import java.math.BigDecimal;

public class ModifiedTransaction extends Transaction{

    private BigDecimal total;


    public ModifiedTransaction(Transaction t, BigDecimal total) {
        super(t.getId(), t.getSaleDate(), t.getProducts());
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return super.toString() +" total: "+total;
    }
}
