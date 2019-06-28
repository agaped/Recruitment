package com.awin.recruitment.implementation;

import com.awin.recruitment.kafka.model.Transaction;
import com.awin.recruitment.library.Consumer;

public class ConsumerImpl<T> implements Consumer<Transaction> {

    @Override
    public void consume(Iterable<Transaction> messages) {

    }
}
