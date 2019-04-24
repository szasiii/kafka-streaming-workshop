package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;

public class Ex4Consumer {
    public static void main(String[] args) throws Exception {
        Consumer<String, String> consumer = createConsumer(Arrays.asList("ex4-output"), StringDeserializer.class.getName(), StringDeserializer.class.getName());

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex4-output").forEach(record -> System.out.println("one-many out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}
