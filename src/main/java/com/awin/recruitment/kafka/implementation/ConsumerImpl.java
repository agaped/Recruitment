package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ConsumerImpl<T> implements Consumer<Transaction>, Runnable {

    private final Logger LOG = LogManager.getLogger(getClass());

    private final BlockingQueue<Transaction> transactions;
    private final List<Transaction> input=new ArrayList<>();

    public ConsumerImpl(BlockingQueue<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void consume(Iterable<Transaction> messages) {
        messages.forEach(t -> this.input.add(t));
        LOG.info("data consumed");
    }

    @Override
    public void run() {
        LOG.info("Consumer thread of id {} started", Thread.currentThread().getId());
        input.forEach(t ->
        {
            try {
                this.transactions.put(t);
                LOG.info("Transaction of id {} consumed successfully", t.getId());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.error("Error when processing transaction of id {}", t.getId());
            }
        });
        try {
            this.transactions.put(new Transaction(999,null,null));
        } catch (InterruptedException e) {
            LOG.error("Error when generating exit transaction id");
        }
        LOG.info("Consumer thread of id {} finished", Thread.currentThread().getId());
    }
}
