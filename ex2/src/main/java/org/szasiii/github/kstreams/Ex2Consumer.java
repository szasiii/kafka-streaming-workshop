package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;

public class Ex2Consumer {
    public static void main(String[] args) {

        Consumer<String, Long> consumer = createConsumer(Arrays.asList("ex2-legacy-output", "ex2-new-output"), StringDeserializer.class.getName(), LongDeserializer.class.getName());

        while (true) {
            ConsumerRecords<String, Long> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex2-new-output").forEach(record -> System.out.println("new out key: " + record.key() + " value: " + record.value()));
                consumerRecords.records("ex2-legacy-output").forEach(record -> System.out.println("legacy out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}
