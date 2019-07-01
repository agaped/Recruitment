package com.awin.recruitment;

import com.awin.recruitment.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.awin.recruitment.kafka.services.TransactionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class RecruitmentApp {

    private RecruitmentApp() { }

    public static void main(
        String[] args
    ) {

        ClassPathXmlApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.create();

        System.out.println("Recruitment app is running");

        TransactionService service = applicationContext.getBean(
                "transactionService", TransactionService.class);
        service.processTransactions(applicationContext.getBean(
                "transactions", Iterable.class));

    }
}
