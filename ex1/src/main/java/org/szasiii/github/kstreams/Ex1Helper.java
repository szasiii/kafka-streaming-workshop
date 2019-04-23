package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;
import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex1Helper {
    public static void main(String[] args) throws Exception {

        Producer<String, String> producer = createProducer();

        producer.send(exRecord("ex1-stream-input", "test1", "test"));
        producer.send(exRecord("ex1-stream-input", "test2", "test"));
        producer.send(exRecord("ex1-stream-input", "test3", "test"));
        producer.send(exRecord("ex1-stream-input", "test1", "test-update"));
        producer.send(exRecord("ex1-stream-input", "test2", null));


        producer.send(exRecord("ex1-table-input", "test1", "test"));
        producer.send(exRecord("ex1-table-input", "test2", "test"));
        producer.send(exRecord("ex1-table-input", "test3", "test"));
        producer.send(exRecord("ex1-table-input", "test1", "test-update"));
        producer.send(exRecord("ex1-table-input", "test2", null));

        Thread.sleep(5000L);

        Consumer<String, String> consumer = createConsumer(Arrays.asList("ex1-stream-output", "ex1-table-output"));

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex1-stream-output").forEach(record -> System.out.println("stream out key: " + record.key() + " value: " + record.value()));
                consumerRecords.records("ex1-table-output").forEach(record -> System.out.println("table out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}


