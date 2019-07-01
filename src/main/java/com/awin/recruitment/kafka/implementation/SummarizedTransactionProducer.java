package com.awin.recruitment.kafka.implementation;

import com.awin.recruitment.kafka.model.SummarizedTransaction;
import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SummarizedTransactionProducer implements Producer<SummarizedTransaction>, Runnable {

    private static final long EXIT_MESSAGE_ID = Long.MIN_VALUE;
    private final Logger log = LogManager.getLogger(getClass());

    private final BlockingQueue<Transaction> transactions;
    private final List<SummarizedTransaction> output=new ArrayList<>();

    public SummarizedTransactionProducer(BlockingQueue<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void produce(Iterable<SummarizedTransaction> messages) {
        Transaction t;
        try {
            while((t =this.transactions.take()).getId()!=EXIT_MESSAGE_ID) {
                SummarizedTransaction summarizedTransaction = SummarizedTransaction.sumTransaction(t);
                this.output.add(summarizedTransaction);
                log.info("Transaction of id {} updated successfully with total {}",
                        summarizedTransaction.getId(), summarizedTransaction.getTotal());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error when updating transaction");
        }
    }

    @Override
    public void run() {
        log.info("Producer {} started", Thread.currentThread().getId());

        produce(this.output);

        log.info("Producer {} finished", Thread.currentThread().getId());
    }

    public List<SummarizedTransaction> getOutput() {
        return Collections.unmodifiableList(this.output);
    }
}
