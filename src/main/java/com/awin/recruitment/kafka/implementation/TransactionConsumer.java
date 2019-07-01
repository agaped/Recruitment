package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class TransactionConsumer implements Consumer<Transaction>, Runnable {
    private final Logger log = LogManager.getLogger(getClass());

    private static final long EXIT_MESSAGE_ID = Long.MIN_VALUE;
    private static final LocalDate EXIT_MESSAGE_DATE = LocalDate.of(1,1,1);

    private final BlockingQueue<Transaction> transactions;
    private final List<Transaction> input=new ArrayList<>();

    public TransactionConsumer(BlockingQueue<Transaction> transactions) {
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
        this.input.forEach(this::putTransaction);
        this.sendExitMessage();
        log.info("Consumer {} finished", Thread.currentThread().getId());
    }

    private void putTransaction(Transaction t) {
            try {
                this.transactions.put(t);
                log.info("Transaction of id {} consumed successfully", t.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Error when processing transaction of id {}", t.getId());
            }
    }

    private void sendExitMessage() {
        try {
            this.transactions.put(new Transaction(EXIT_MESSAGE_ID,EXIT_MESSAGE_DATE,
                    Collections.emptyList()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error when generating exit message");
        }
    }
}
