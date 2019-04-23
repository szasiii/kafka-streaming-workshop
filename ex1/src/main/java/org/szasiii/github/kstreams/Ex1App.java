package org.szasiii.github.kstreams;

import org.apache.kafka.streams.Topology;

import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createTopics;

public class Ex1App {
    public static void main(String[] args) throws Exception {

        createTopics(Arrays.asList("ex1-stream-input", "ex1-table-input", "ex1-stream-output", "ex1-table-output"));

        /*
         * 1. Define properties for application
         * In createTopology method:
         *  a. Create StreamsBuilder instance
         *  b. Create source KStream out of input topic 'ex1-input'
         *  c. Sink kstream to output topic 'ex1-stream-output'
         *  d. Create sourceKTable out of input topic 'ex1-input'
         *  e. Sink ktable to output topic 'ex1-table-output'
         * 2. Create KafkaStreams instance
         * 3. Start kafka streams
         */

//        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    private static Topology createTopology() {
        //TODO: Your solution

        return null;
    }
}
