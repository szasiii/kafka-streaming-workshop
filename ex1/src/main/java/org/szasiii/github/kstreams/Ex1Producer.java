package org.szasiii.github.kstreams;

import org.apache.kafka.clients.producer.Producer;

import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex1Producer {
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
    }
}


