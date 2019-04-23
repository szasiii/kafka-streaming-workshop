package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;

public class Ex1Consumer {
    public static void main(String[] args) throws Exception {
        Consumer<String, String> consumer = createConsumer(Arrays.asList("ex1-stream-output", "ex1-table-output"), StringSerializer.class.getName());

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex1-stream-output").forEach(record -> System.out.println("stream out key: " + record.key() + " value: " + record.value()));
                consumerRecords.records("ex1-table-output").forEach(record -> System.out.println("table out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}
