package org.szasiii.github.kstreams;

import org.apache.kafka.clients.producer.Producer;

import static org.szasiii.kstreams.Utils.createProducer;
import static org.szasiii.kstreams.Utils.exRecord;

public class Ex3Producer {
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

    }
}
