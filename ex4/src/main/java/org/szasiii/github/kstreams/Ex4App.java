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

@SuppressWarnings("all")
public class Ex4App implements TopologyProvider {
    public static void main(String[] args) throws Exception {

        /***
         * You need to join 2 topics ex4-doctors and ex4-clinics both are json/POJO serialized
         * Serdes and Serializers have already been prepared for you
         * For both doctors and clinics its primary key is used as message key
         * This time key for join is inside of Clinic pojo (dockorId)
         * You will have to figure out how to use Kafka Streams API methods to create one-to-many join
         * Then you will have to flatten that one-to-many structure
         * to get final output topic ex4-output in format:
         * long value key (doctor id)
         * string value message {doctor.id}-{doctor.specialityCode}-{clinic.id}
         *
         */

        Ex4App ex4App = new Ex4App();
        createTopics(Arrays.asList("ex4-doctors", "ex4-clinics", "ex4-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "ex4-app5");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(ex4App.createTopology(), config);
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
