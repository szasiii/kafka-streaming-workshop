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
public class Ex5App implements TopologyProvider {


    /***
     *
     * Create global table of user data and stream of orders then join orders data
     * with inner and left join so that message with remain having key of order
     * and value in format of "Order=orderValue, UserInfo=[userInfoValue]"
     * take into account 'null' values in case of left join
     *
     */


    public static void main(String[] args) throws Exception {
        Ex5App ex5App = new Ex5App();
        createTopics(Arrays.asList("ex5-doctors", "ex5-diseases", "ex5-prescriptions", "ex5-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "ex5-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.TOPOLOGY_OPTIMIZATION, "all");

        final KafkaStreams kafkaStreams = new KafkaStreams(ex5App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        //TODO Your solution
        return streamsBuilder.build();
    }
}
