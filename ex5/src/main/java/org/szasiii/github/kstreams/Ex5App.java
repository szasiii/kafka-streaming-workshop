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
     * This time run docker-compose down to shut down previous run
     * and start docker-compose --file docker-compose-ex5.yaml up -d
     *
     * There are 3 topics streamed via kafka connect
     * ex5-doctors, ex5-diseases, ex5-prescriptions
     * Each of them has key in json format {"id": 1} and json value
     * For both ID (ID is name of model for key) and each value format
     * models/serializers/deserializers/serdes have already been prepared for you
     * you will have to take a look on data and join/map/aggregate however you want
     * to obtain at the end ex5-output topic where key will be long value diseaseId from Disease pojo
     * and value will be json containing doctorId and prescripted medicineName for that disease
     *
     * Take a note that you will have to introduce new data types, serializers, deserializers and serdes
     * that is easy you can base on implementation done in infra
     *
     * Intentionally there is no test for that exercise
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
