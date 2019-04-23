package org.szasiii.github.kstreams;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import static org.szasiii.kstreams.Utils.createCustomProducer;

public class Ex1Producer {
    public static void main(String[] args) throws Exception {

        Producer<Long, String> producer = createCustomProducer(LongSerializer.class.getName(), StringSerializer.class.getName());

        producer.send(new ProducerRecord<>("ex1-stream-input", 1L, "test"));
        producer.send(new ProducerRecord<>("ex1-stream-input", 2L, "test"));
        producer.send(new ProducerRecord<>("ex1-stream-input", 3L, "test"));
        producer.send(new ProducerRecord<>("ex1-stream-input", 1L, "test-update"));
        producer.send(new ProducerRecord<>("ex1-stream-input", 2L, null));


        producer.send(new ProducerRecord<>("ex1-table-input", 1L, "test"));
        producer.send(new ProducerRecord<>("ex1-table-input", 2L, "test"));
        producer.send(new ProducerRecord<>("ex1-table-input", 3L, "test"));
        producer.send(new ProducerRecord<>("ex1-table-input", 1L, "test-update"));
        producer.send(new ProducerRecord<>("ex1-table-input", 2L, null));

        Thread.sleep(5000L);
    }
}


