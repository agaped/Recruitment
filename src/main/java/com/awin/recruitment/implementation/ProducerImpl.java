package com.awin.recruitment.implementation;

import com.awin.recruitment.kafka.model.ModifiedTransaction;
import com.awin.recruitment.kafka.model.Product;
import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProducerImpl<T> implements Producer<Transaction> {

    private static final Logger LOG = LogManager.getLogger(Producer.class);

    private List<ModifiedTransaction> modifiedTransactions;

    @Override
    public void produce(Iterable<Transaction> messages) {
        modifiedTransactions = new ArrayList<>();
        BigDecimal sum;

        for (Transaction t : messages) {
            sum=t.getProducts().stream()
                    .map(getPricesAsStream())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            modifiedTransactions.add(new ModifiedTransaction(t.getId(), t.getSaleDate(), t.getProducts(), sum));
        }
        LOG.info("transactions modified = {}", modifiedTransactions);
    }

    private Function<Product, BigDecimal> getPricesAsStream() {
        return Product::getPrice;
    }
}
