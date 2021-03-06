package org.szasiii.github.kstreams;

import org.apache.kafka.clients.producer.Producer;

import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex2Producer {
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
    }
}
