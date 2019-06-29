package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.ModifiedTransaction;
import com.awin.recruitment.kafka.model.Product;
import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class ProducerImpl<T> implements Producer<ModifiedTransaction>, Runnable {

    private static final long EXIT_MESSAGE_ID = Long.MIN_VALUE;
    private final Logger LOG = LogManager.getLogger(getClass());

    private final BlockingQueue<Transaction> transactions;
    private final List<ModifiedTransaction> output=new ArrayList<>();

    public ProducerImpl(BlockingQueue<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void produce(Iterable<ModifiedTransaction> messages) {
        Transaction t;
        try {
            while((t =this.transactions.take()).getId()!=EXIT_MESSAGE_ID) {
                ModifiedTransaction modifiedTransaction = new ModifiedTransaction(t, countTotal(t));
                this.output.add(modifiedTransaction);
                LOG.info("Transaction of id {} updated successfully with total {}",
                        modifiedTransaction.getId(), modifiedTransaction.getTotal());
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            LOG.error("Error when updating transaction");
        }
    }

    @Override
    public void run() {
        LOG.info("Producer {} started", Thread.currentThread().getId());

        produce(this.output);

        LOG.info("Producer {} finished", Thread.currentThread().getId());
    }

    private BigDecimal countTotal(Transaction t) {
        return t.getProducts().stream()
                .map(getPrices())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Function<Product, BigDecimal> getPrices() {
        return Product::getPrice;
    }
}
