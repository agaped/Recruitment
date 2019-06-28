package com.awin.recruitment.kafka.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ModifiedTransaction extends Transaction{

    private BigDecimal total;


    public ModifiedTransaction(int id, LocalDate saleDate, List<Product> products, BigDecimal total) {
        super(id, saleDate, products);
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
