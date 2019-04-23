package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

public class Ex3App {
    /***
     *
     * Create global table of user data and stream of orders then join orders data
     * with inner and left join so that message with remain having key of order
     * and value in format of "Order=orderValue, UserInfo=[userInfoValue]"
     * take into account 'null' values in case of left join
     *
     */


    public static void main(String[] args) throws Exception {
        createTopics(Arrays.asList("ex3-user-table", "ex3-user-orders", "ex3-inner-join-output", "ex3-left-join-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "ex3-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    private static Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        //TODO Your solution
        return streamsBuilder.build();
    }
}


