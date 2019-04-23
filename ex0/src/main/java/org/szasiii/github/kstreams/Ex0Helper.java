package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;

import static org.szasiii.kstreams.Utils.createConsumer;
import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex0Helper {
    public static void main(String[] args) throws Exception {

        Producer<String, String> producer = createProducer();

        producer.send(exRecord("ex0-input", "test1", "test"));
        producer.send(exRecord("ex0-input", "test2", "test"));
        producer.send(exRecord("ex0-input", "test3", "test"));
        producer.send(exRecord("ex0-input", "test1", "test-update"));
        producer.send(exRecord("ex0-input", "test2", null));

        Thread.sleep(5000L);

        Consumer<String, String> consumer = createConsumer(Collections.singletonList("ex0-output"), StringSerializer.class.getName());

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex0-output").forEach(record -> System.out.println("stream ex0 out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}
