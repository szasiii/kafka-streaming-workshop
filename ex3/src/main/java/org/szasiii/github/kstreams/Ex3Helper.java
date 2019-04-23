package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;
import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex3Helper {
    public static void main(String[] args) throws Exception {

        Producer<String, String> producer = createProducer();

        producer.send(exRecord("ex3-user-table", "bambi", "Name=bambi,Feature=cuteness")).get();
        producer.send(exRecord("ex3-user-orders", "bambi", "Apples and Flowers (1)")).get();

        Thread.sleep(5000L);


        producer.send(exRecord("ex3-user-orders", "godzilla", "Tokyo (1)")).get();

        Thread.sleep(5000L);


        producer.send(exRecord("ex3-user-table", "bambi", "Name=bambi,Feature=ultra-cuteness")).get();
        producer.send(exRecord("ex3-user-orders", "bambi", "Flowers (3)")).get();

        Thread.sleep(5000L);


        producer.send(exRecord("ex3-user-orders", "batman", "Computer (4)")).get();
        producer.send(exRecord("ex3-user-table", "batman", "Name=batman,Feature=money")).get();
        producer.send(exRecord("ex3-user-orders", "batman", "Justice (4)")).get();
        producer.send(exRecord("ex3-user-table", "batman", null)).get();

        Thread.sleep(5000L);


        producer.send(exRecord("ex3-user-table", "ironman", "First=ironman,Feature=more-money")).get();
        producer.send(exRecord("ex3-user-table", "ironman", null)).get();
        producer.send(exRecord("ex3-user-orders", "ironman", "Ironsuit (47)")).get();


        Thread.sleep(5000L);

        Consumer<String, String> consumer = createConsumer(Arrays.asList("ex3-inner-join-output", "ex3-left-join-output"), StringDeserializer.class.getName());

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex3-inner-join-output").forEach(record -> System.out.println("inner out key: " + record.key() + " value: " + record.value()));
                consumerRecords.records("ex3-left-join-output").forEach(record -> System.out.println("left out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}
