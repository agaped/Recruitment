package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ConsumerImpl implements Consumer<Transaction>, Runnable {

    private static final long EXIT_MESSAGE_ID = Long.MIN_VALUE;
    private final Logger log = LogManager.getLogger(getClass());

    private final BlockingQueue<Transaction> transactions;
    private final List<Transaction> input=new ArrayList<>();

    public ConsumerImpl(BlockingQueue<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void consume(Iterable<Transaction> messages) {
        messages.forEach(this.input::add);
        log.info("Data consumed");
    }

    @Override
    public void run() {
        log.info("Consumer {} started", Thread.currentThread().getId());
        this.input.forEach(t ->
        {
            try {
                this.transactions.put(t);
                log.info("Transaction of id {} consumed successfully", t.getId());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Error when processing transaction of id {}", t.getId());
            }
        });
        sendExitMessage();
        log.info("Consumer {} finished", Thread.currentThread().getId());
    }

    private void sendExitMessage() {
        try {
            this.transactions.put(new Transaction(EXIT_MESSAGE_ID,null,null));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error when generating exit message");
        }
    }

    public Collection<Transaction> getTransactions() {
        return Collections.unmodifiableCollection(this.transactions);
    }
}
