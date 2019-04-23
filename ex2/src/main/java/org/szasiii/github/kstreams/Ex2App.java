package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.szasiii.kstreams.TopologyProvider;

import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

@SuppressWarnings("Duplicates")
public class Ex2App implements TopologyProvider {

    /***
     * Unified orders produce data 1 input topic for legacy and new systems
     * Message's key as indicator of legacy or new system [legacy|new]
     * Messages start with product name (bambi, godzilla, optimus) then is separator':' batch of quantity
     * numbers separated with commas (number can be negative for number of returned pieces) {productName}:1,1,2,-3
     * Filter invalid products as our analysis needs only bambi, godzilla and optimus to be investigated
     * as a result we need create 2 output topics ex2-legacy-out and ex2-new-output each of them
     * should have sum for each of desired products
     *
     * You can assume no malformed records
     *
     */


    public static void main(String[] args) throws Exception {
        Ex2App ex2App = new Ex2App();
        createTopics(Arrays.asList("ex2-input", "ex2-legacy-output", "ex2-new-input"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "solution1-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(ex2App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        //TODO: Your solution
        return streamsBuilder.build();
    }
}
