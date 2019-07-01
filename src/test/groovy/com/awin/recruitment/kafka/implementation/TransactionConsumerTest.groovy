import com.awin.recruitment.kafka.data.TransactionGenerator
import com.awin.recruitment.kafka.implementation.TransactionConsumer
import spock.lang.Specification

import java.util.concurrent.ArrayBlockingQueue

class TransactionConsumerTest extends Specification {

    def "test-transactions-generated-correctly"() {
        setup: "create consumer object"
        def queue = new ArrayBlockingQueue(10)
        def consumer = new TransactionConsumer(queue)
        def exitMessage = TransactionGenerator.generateExitMessage()
        def messagesToProcess = TransactionGenerator.generateMessages()

        when: "consumer gets messages and starts work"
        consumer.consume(messagesToProcess)
        consumer.run()

        then:
        def commons = queue.intersect(messagesToProcess)
        def difference = queue.plus(messagesToProcess)
        difference.removeAll(commons)
        assert difference.getFirst().equals(exitMessage)
    }

}
