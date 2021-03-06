package org.szasiii.github.kstreams;

import org.apache.kafka.streams.Topology;
import org.szasiii.kstreams.TopologyProvider;

import java.util.Arrays;

import static org.szasiii.kstreams.Utils.createTopics;

public class Ex1App implements TopologyProvider {
    public static void main(String[] args) throws Exception {
        Ex1App ex1App = new Ex1App();

        createTopics(Arrays.asList("ex1-stream-input", "ex1-table-input", "ex1-stream-output", "ex1-table-output"));

        /**
         * 1. Define properties for application
         * In createTopology method:
         *  a. Create StreamsBuilder instance
         *  b. Create source KStream out of input topic 'ex1-stream-input'
         *  c. Sink kstream to output topic 'ex1-stream-output'
         *  d. Create sourceKTable out of input topic 'ex1-stream-input'
         *  e. Sink ktable to output topic 'ex1-table-output'
         * 2. Create KafkaStreams instance
         * 3. Start kafka streams
         * 4. Create instances of producer and consumer to observe result
         * */

//        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        return null;
    }
}
