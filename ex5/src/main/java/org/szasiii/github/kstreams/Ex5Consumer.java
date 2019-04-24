package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.szasiii.github.infra.deserializers.ArrayListDeserializer;
import org.szasiii.github.infra.solution.FinalData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createConsumer;

public class Ex5Consumer {
    public static void main(String[] args) throws Exception {
        Consumer<Long, ArrayList<FinalData>> consumer = createConsumer(Arrays.asList("ex5-output"), LongDeserializer.class.getName(), ArrayListDeserializer.class.getName());

        while (true) {
            ConsumerRecords<Long, ArrayList<FinalData>> consumerRecords = consumer.poll(Duration.ofSeconds(2L));
            if (!consumerRecords.isEmpty()) {
                consumerRecords.records("ex5-output").forEach(record -> System.out.println("ex5 out key: " + record.key() + " value: " + record.value().toString()));
            }
        }
    }
}
