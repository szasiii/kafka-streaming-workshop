package org.szasiii.github.kstreams.solution;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.szasiii.kstreams.TopologyProvider;

import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

public class Solution2App implements TopologyProvider {
    public static void main(String[] args) throws Exception {
        Solution2App solution2App = new Solution2App();
        createTopics(Arrays.asList("ex2-input", "ex2-legacy-output", "ex2-new-input"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "solution1-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(solution2App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> allOrders = streamsBuilder.stream("ex2-input", Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, KeyValue<String, String>> transformedStream = allOrders
                .mapValues(value -> Arrays.asList(value.split(":")))
                .mapValues(value -> KeyValue.pair(value.get(0), value.get(1)));

        transformedStream
                .filter((key, value) -> key.equals("legacy"))
                .map((key, value) -> value)
                .flatMapValues(value -> Arrays.asList(value.split(",")))
                .mapValues(value -> Long.valueOf(value))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.Long()))
                .reduce(Long::sum)
                .toStream()
                .to("ex2-legacy-output", Produced.with(Serdes.String(), Serdes.Long()));

        transformedStream
                .filter((key, value) -> key.equals("new"))
                .map((key, value) -> value)
                .flatMapValues(value -> Arrays.asList(value.split(",")))
                .mapValues(value -> Long.valueOf(value))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.Long()))
                .reduce(Long::sum)
                .toStream()
                .to("ex2-new-output", Produced.with(Serdes.String(), Serdes.Long()));

        return streamsBuilder.build();
    }
}
