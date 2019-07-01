package com.awin.recruitment.kafka.data;

import com.awin.recruitment.kafka.model.Product;
import com.awin.recruitment.kafka.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionGenerator {

    private static long EXIT_MESSAGE_ID = Long.MIN_VALUE;
    private static LocalDate EXIT_MESSAGE_DATE = LocalDate.of(1, 1, 1);

    private TransactionGenerator() {
    }

    public static List<Transaction> generateMessages(){

        Product bike1=new Product("bike1", BigDecimal.valueOf(2433.89));
        Product bike2=new Product("bike2", BigDecimal.valueOf(3200.99));
        Product bike3=new Product("bike3", BigDecimal.valueOf(1699.90));
        Product bike4=new Product("bike4", BigDecimal.valueOf(2555.00));
        Product bike5=new Product("bike5", BigDecimal.valueOf(1990.50));
        Product bike6=new Product("bike6", BigDecimal.valueOf(2789.90));

        Transaction transaction1=new Transaction(1, LocalDate.now(),Arrays.asList(bike1,bike2));
        Transaction transaction2=new Transaction(2, LocalDate.now(),Arrays.asList(bike3,bike4));
        Transaction transaction3=new Transaction(3, LocalDate.now(),Arrays.asList(bike5,bike6));
        Transaction transaction4=new Transaction(4, LocalDate.now(),Arrays.asList(bike1,bike6));
        Transaction transaction5=new Transaction(5, LocalDate.now(),Arrays.asList(bike2,bike4));
        Transaction transaction6=new Transaction(6, LocalDate.now(),Arrays.asList(bike3,bike5));

        return Arrays.asList(transaction1,transaction2,transaction3,
                transaction4, transaction5,transaction6);
    }

    public static Transaction generateExitMessage() {
        return new Transaction(EXIT_MESSAGE_ID, EXIT_MESSAGE_DATE, Collections.emptyList());
    }
}
