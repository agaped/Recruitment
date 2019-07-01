package com.awin.recruitment.kafka.services;

import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionService<T> {

    private final Logger log = LogManager.getLogger(getClass());

    private final Consumer<T> consumer;
    private final Producer<T> producer;

    public TransactionService(Consumer consumer, Producer producer) {
        this.consumer = consumer;
        this.producer = producer;
    }

    public void processTransactions(Iterable<T> messages) {
        log.info("Transaction processing started ...");

        this.consumer.consume(messages);

        Thread consumerThread = new Thread((Runnable) consumer);
        Thread producerThread = new Thread((Runnable) producer);
        consumerThread.start();
        producerThread.start();

        try {
            consumerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Transaction processing interrupted");
        }

        log.info("Transaction processing finished");
    }
}
