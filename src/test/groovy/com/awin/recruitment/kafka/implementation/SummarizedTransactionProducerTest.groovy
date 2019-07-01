package com.awin.recruitment.kafka.implementation

import com.awin.recruitment.kafka.data.TransactionGenerator
import com.awin.recruitment.kafka.model.Transaction
import spock.lang.Specification

import java.util.concurrent.ArrayBlockingQueue

class SummarizedTransactionProducerTest extends Specification {

    def "test-transactions-generated-correctly"() {
        setup: "create producer object"
        def queue=new ArrayBlockingQueue<Transaction>(10,true,TransactionGenerator.generateMessages())
        queue.add(TransactionGenerator.generateExitMessage())
        def producer = new SummarizedTransactionProducer(queue)

        when: "producer starts work"
        producer.run()

        then:
        def expectedOutput=[BigDecimal.valueOf(5634.88), BigDecimal.valueOf(4254.9),
            BigDecimal.valueOf(4780.4), BigDecimal.valueOf(5223.79),
            BigDecimal.valueOf(5755.99), BigDecimal.valueOf(3690.4)]
        def output=producer.getOutput()
        for (int i = 0; i <output.size() ; i++) {
            assert output.get(i).getTotal().equals(expectedOutput[i])
        }
    }

}


