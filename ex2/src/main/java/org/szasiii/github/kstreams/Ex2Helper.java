package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;
import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex2Helper {
    public static void main(String[] args) throws Exception {

        Producer<String, String> producer = createProducer();

        producer.send(exRecord("ex2-input", "new", "bambi:2,4,5,1,3,4,5,-1,23,-11"));
        producer.send(exRecord("ex2-input", "new", "batman:2,3"));
        producer.send(exRecord("ex2-input", "new", "optimus:1"));
        producer.send(exRecord("ex2-input", "new", "godzilla:1000,-20,420"));
        producer.send(exRecord("ex2-input", "new", "fake:0"));
        producer.send(exRecord("ex2-input", "legacy", "bambi:2,4,5,10,3,4,5,-10,23,-11"));
        producer.send(exRecord("ex2-input", "legacy", "batman:2,3"));
        producer.send(exRecord("ex2-input", "legacy", "optimus:100"));
        producer.send(exRecord("ex2-input", "legacy", "godzilla:1,-20,420"));
        producer.send(exRecord("ex2-input", "legacy", "fake:0"));


        Thread.sleep(5000L);

        Consumer<String, Long> consumer = createConsumer(Arrays.asList("ex2-legacy-output", "ex2-new-output"), LongDeserializer.class.getName());

        while (true) {
            ConsumerRecords<String, Long> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex2-new-output").forEach(record -> System.out.println("new out key: " + record.key() + " value: " + record.value()));
                consumerRecords.records("ex2-legacy-output").forEach(record -> System.out.println("legacy out key: " + record.key() + " value: " + record.value()));
            }
        }
    }
}

//    legacy out key: bambi value: 35
//        legacy out key: batman value: 5
//        legacy out key: optimus value: 100
//        legacy out key: godzilla value: 401
//        legacy out key: fake value: 0
//        new out key: bambi value: 35
//        new out key: batman value: 5
//        new out key: optimus value: 1
//        new out key: godzilla value: 1400
//        new out key: fake value: 0
