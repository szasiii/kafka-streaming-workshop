package org.szasiii.github.kstreams.solution;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.szasiii.kstreams.TopologyProvider;

import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

public class Solution3App implements TopologyProvider {

    public static void main(String[] args) throws Exception {
        Solution3App solution3App = new Solution3App();
        createTopics(Arrays.asList("ex3-user-table", "ex3-user-orders", "ex3-inner-join-output", "ex3-left-join-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "ex3-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(solution3App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        GlobalKTable<String, String> usersGlobalTable = streamsBuilder.globalTable("ex3-user-table");
        KStream<String, String> userOrders = streamsBuilder.stream("ex3-user-orders");
        KStream<String, String> userOrdersEnrichedJoin =
                userOrders.join(usersGlobalTable,
                        (key, value) -> key,
                        (userOrder, userInfo) -> "Order=" + userOrder + ",UserInfo=[" + userInfo + "]"
                );
        userOrdersEnrichedJoin.to("ex3-inner-join-output");
        KStream<String, String> userOrdersEnrichedLeftJoin =
                userOrders.leftJoin(usersGlobalTable,
                        (key, value) -> key,
                        (userOrder, userInfo) -> {
                            if (userInfo != null) {
                                return "Order=" + userOrder + ",UserInfo=[" + userInfo + "]";
                            } else {
                                return "Order=" + userOrder + ",UserInfo=null";
                            }
                        }
                );
        userOrdersEnrichedLeftJoin.to("ex3-left-join-output");
        return streamsBuilder.build();
    }
}
