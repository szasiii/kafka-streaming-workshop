package org.szasiii.github.kstreams;

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
import org.szasiii.github.infra.model.Disease;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.model.Id;
import org.szasiii.github.infra.model.Prescription;
import org.szasiii.github.infra.serdes.ArrayListSerde;
import org.szasiii.github.infra.serdes.CustomSerdes;
import org.szasiii.github.infra.solution.FinalData;
import org.szasiii.github.infra.solution.FinalDeserializer;
import org.szasiii.github.infra.solution.FinalSerializer;
import org.szasiii.github.infra.solution.FullData;
import org.szasiii.github.infra.solution.FullDataDeserializer;
import org.szasiii.github.infra.solution.FullDataSerializer;
import org.szasiii.github.infra.solution.PDDeserializer;
import org.szasiii.github.infra.solution.PDSerializer;
import org.szasiii.github.infra.solution.PrescriptionDisease;
import org.szasiii.kstreams.TopologyProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

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

        KTable<Long, Doctor> doctors =
                streamsBuilder
                        .stream("ex5-doctors", Consumed.with(CustomSerdes.ID, CustomSerdes.DOCTOR))
                        .map((key, value) -> KeyValue.pair(key.getId(), value))
                        .groupByKey(Serialized.with(Serdes.Long(), CustomSerdes.DOCTOR))
                        .reduce((value1, value2) -> value2, Materialized.with(Serdes.Long(), CustomSerdes.DOCTOR));

        KTable<Long, Disease> diseases =
                streamsBuilder.stream("ex5-diseases", Consumed.with(CustomSerdes.ID, CustomSerdes.DISASE))
                        .map((key, value) -> KeyValue.pair(key.getId(), value))
                        .groupByKey(Serialized.with(Serdes.Long(), CustomSerdes.DISASE))
                        .reduce((value1, value2) -> value2, Materialized.with(Serdes.Long(), CustomSerdes.DISASE));

        KTable<Id, Prescription> presciptions =
                streamsBuilder.table("ex5-prescriptions", Consumed.with(CustomSerdes.ID, CustomSerdes.PRESCRIPTION)); //filter where no disease or medicine code


        KTable<Long, PrescriptionDisease> kTable2 = presciptions
                .groupBy((key, value) -> KeyValue.pair(value.getDiseaseid(), value), Serialized.with(Serdes.Long(), CustomSerdes.PRESCRIPTION))
                .aggregate(ArrayList::new, (key, value, aggregate) -> {
                    aggregate.add(value);
                    return new ArrayList<>(aggregate);
                }, (key, value, aggregate) -> {
                    aggregate.remove(value);
                    return new ArrayList<>(aggregate);
                }, Materialized.with(Serdes.Long(), new ArrayListSerde<>(CustomSerdes.PRESCRIPTION)))
                .toStream()
                .flatMapValues(value -> value)
                .join(diseases, PrescriptionDisease::new)
                .selectKey((key, value) -> value.getPrescription().getId())
                .groupByKey(Serialized.with(Serdes.Long(), Serdes.serdeFrom(new PDSerializer(), new PDDeserializer())))
                .reduce((value1, value2) -> value2);


        kTable2
                .groupBy((key, value) -> KeyValue.pair(value.getPrescription().getDoctorid(), value), Serialized.with(Serdes.Long(), Serdes.serdeFrom(new PDSerializer(), new PDDeserializer())))
                .aggregate(ArrayList::new, (key, value, aggregate) -> {
                    aggregate.add(value);
                    return new ArrayList<>(aggregate);
                }, (key, value, aggregate) -> {
                    aggregate.remove(value);
                    return new ArrayList<>(aggregate);
                }, Materialized.with(Serdes.Long(), new ArrayListSerde<>(Serdes.serdeFrom(new PDSerializer(), new PDDeserializer()))))
                .toStream()
                .flatMapValues(value -> value)
                .join(doctors, FullData::new)
                .groupBy((key, value) -> value.getPrescriptionDisaese().getDisease().getId(), Serialized.with(Serdes.Long(), Serdes.serdeFrom(new FullDataSerializer(), new FullDataDeserializer())))
                .aggregate(ArrayList::new, (key, value, aggregate) -> {
                    aggregate.add(new FinalData(value.getDoctor().getId(), value.getPrescriptionDisaese().getPrescription().getMedicinename()));
                    return aggregate;
                }, Materialized.with(Serdes.Long(), new ArrayListSerde<>(Serdes.serdeFrom(new FinalSerializer(), new FinalDeserializer()))))
                .toStream()
                .to("ex5-output", Produced.with(Serdes.Long(), new ArrayListSerde<>(Serdes.serdeFrom(new FinalSerializer(), new FinalDeserializer()))));


        //TODO Your solution
        return streamsBuilder.build();
    }
}
