package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.ModifiedTransaction;
import com.awin.recruitment.kafka.model.Product;
import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class ProducerImpl<T> implements Producer<Transaction>, Runnable {

    private final Logger LOG = LogManager.getLogger(getClass());

    private final BlockingQueue<Transaction> transactions;

    public ProducerImpl(BlockingQueue<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void produce(Iterable<Transaction> messages) {
        this.run();
    }

    @Override
    public void run() {
        LOG.info("Producer thread of id {} started", Thread.currentThread().getId());

        Transaction t;
        try {
            while((t =this.transactions.take()).getId()!=999) {
                new ModifiedTransaction(t, countTotal(t));
                LOG.info("Transaction of id {} updated successfully with total {}", t.getId(), countTotal(t));
                Thread.sleep(2000);
            }
            } catch (InterruptedException e) {
                LOG.error("Error when updating transaction");
            }
        LOG.info("Producer thread of id {} finished", Thread.currentThread().getId());
    }

    private BigDecimal countTotal(Transaction t) {
        return t.getProducts().stream()
                .map(getPricesAsStream())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Function<Product, BigDecimal> getPricesAsStream() {
        return Product::getPrice;
    }
}
