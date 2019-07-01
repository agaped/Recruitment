import com.awin.recruitment.kafka.implementation.ConsumerImpl
import com.awin.recruitment.kafka.model.Product
import com.awin.recruitment.kafka.model.Transaction
import spock.lang.Specification

import java.time.LocalDate
import java.util.concurrent.ArrayBlockingQueue

class ConsumerImplTest extends Specification{

    final EXIT_MESSAGE =1

    def "test-transactions-generated-correctly"() {
        setup: "create consumer object"
        def queue=new ArrayBlockingQueue(10)
        def consumer = new ConsumerImpl(queue)

        when: ""
        def messages = generateMessages()
        consumer.consume(messages)
        consumer.run()

        then:
        assert consumer.getTransactions().size()==messages.size()+EXIT_MESSAGE
    }

    List<Transaction> generateMessages(){

        def bike1=new Product("bike1", new BigDecimal(2433.89))
        def bike2=new Product("bike2", new BigDecimal(3200.99))
        def bike3=new Product("bike3", new BigDecimal(1699.90))
        def bike4=new Product("bike4", new BigDecimal(2555.00))
        def bike5=new Product("bike5", new BigDecimal(1990.50))
        def bike6=new Product("bike6", new BigDecimal(2789.90))

        def transaction1=new Transaction(1, LocalDate.now(),Arrays.asList(bike1,bike2))
        def transaction2=new Transaction(2, LocalDate.now(),Arrays.asList(bike3,bike4))
        def transaction3=new Transaction(3, LocalDate.now(),Arrays.asList(bike5,bike6))
        def transaction4=new Transaction(4, LocalDate.now(),Arrays.asList(bike1,bike6))
        def transaction5=new Transaction(5, LocalDate.now(),Arrays.asList(bike2,bike4))
        def transaction6=new Transaction(6, LocalDate.now(),Arrays.asList(bike3,bike5))

        def list=Arrays.asList(transaction1,transaction2,transaction3,
                transaction4, transaction5,transaction6)

        return list
    }

}
