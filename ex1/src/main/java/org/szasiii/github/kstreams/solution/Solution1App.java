package org.szasiii.github.kstreams.solution;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.szasiii.kstreams.TopologyProvider;

import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

@SuppressWarnings("Duplicates")
public class Solution1App implements TopologyProvider {

    public static void main(String[] args) throws Exception {
        Solution1App solution1App = new Solution1App();

        createTopics(Arrays.asList("ex1-stream-input", "ex1-table-input", "ex1-stream-output", "ex1-table-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "solution1-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(solution1App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        streamsBuilder.stream("ex1-stream-input", Consumed.with(Serdes.Long(), Serdes.String())).to("ex1-stream-output");
        streamsBuilder.table("ex1-table-input", Consumed.with(Serdes.Long(), Serdes.String())).toStream().to("ex1-table-output");
        return streamsBuilder.build();
    }

}
