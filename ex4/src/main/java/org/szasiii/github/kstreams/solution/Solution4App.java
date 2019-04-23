package org.szasiii.github.kstreams.solution;

import io.vavr.Tuple2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.szasiii.github.infra.model.Clinic;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.serdes.ArrayListSerde;
import org.szasiii.github.infra.serdes.CustomSerdes;
import org.szasiii.kstreams.TopologyProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

public class Solution4App implements TopologyProvider {
    public static void main(String[] args) throws Exception {
        Solution4App solution4App = new Solution4App();

        createTopics(Arrays.asList("ex4-doctors", "ex4-clinics", "ex4-output"));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "solution4-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(solution4App.createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    @Override
    public Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KTable<Long, Doctor> doctors =
                streamsBuilder.table("ex4-doctors", Consumed.with(Serdes.Long(), CustomSerdes.DOCTOR));

        KTable<Long, Clinic> clinics =
                streamsBuilder.table("ex4-clinics", Consumed.with(Serdes.Long(), CustomSerdes.CLINIC));


        doctors
                .groupBy((key, value) -> KeyValue.pair(value.getClinicId(), value), Serialized.with(Serdes.Long(), CustomSerdes.DOCTOR))
                .aggregate(ArrayList::new, (key, value, aggregate) -> {
                    aggregate.add(value);
                    return new ArrayList<>(aggregate);
                }, (key, value, aggregate) -> {
                    aggregate.remove(value);
                    return new ArrayList<>(aggregate);
                }, Materialized.with(Serdes.Long(), new ArrayListSerde<>(CustomSerdes.DOCTOR)))
                .toStream()
                .flatMapValues((readOnlyKey, value) -> value)
                .join(clinics, Tuple2::new)
                .selectKey((key, value) -> value._1.getId().toString())
                .mapValues(value -> value._1.getId() + "-" + value._1.getSpecialityCode() + "-" + value._2.getId())
                .to("ex4-output", Produced.with(Serdes.String(), Serdes.String()));

        return streamsBuilder.build();
    }
}
