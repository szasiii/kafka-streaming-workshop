package org.szasiii.github.kstreams;

import lombok.AllArgsConstructor;
import lombok.Data;
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
import org.apache.kafka.streams.kstream.Serialized;
import org.szasiii.github.infra.model.Clinic;
import org.szasiii.github.infra.model.Disease;
import org.szasiii.github.infra.model.Doctor;
import org.szasiii.github.infra.model.Medicine;
import org.szasiii.github.infra.model.Prescription;
import org.szasiii.github.infra.serdes.ArrayListSerde;
import org.szasiii.github.infra.serdes.CustomSerdes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.szasiii.kstreams.Utils.createTopics;

public class Ex5App {


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
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "ex5-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(createTopology(), config);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    }

    private Topology createTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KTable<Long, Doctor> doctors =
                streamsBuilder.table("ex5-doctors", Consumed.with(Serdes.Long(), CustomSerdes.DOCTOR)); //filtrer no clinic id and no speciality id

        KTable<Long, Clinic> clinics =
                streamsBuilder.table("ex5-clinics", Consumed.with(Serdes.Long(), CustomSerdes.CLINIC));

        KTable<Long, Disease> diseases =
                streamsBuilder.table("ex5-diseases", Consumed.with(Serdes.Long(), CustomSerdes.DISASE)); // normalize by trimming and lower casing codes

        KTable<Long, Medicine> medicines =
                streamsBuilder.table("ex5-medicines", Consumed.with(Serdes.Long(), CustomSerdes.MEDICINE));

        KTable<Long, Prescription> presciptions =
                streamsBuilder.table("ex5-prescriptions", Consumed.with(Serdes.Long(), CustomSerdes.PRESCRIPTION)); //filter where no disease or medicine code


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
                .join(clinics, DoctorClinic::new)
                .selectKey((key, value) -> KeyValue.pair(value.getDoctor().getId(), value));


        /***
         * join doctors and clinics to doctor-clinic left
         * join doctors-clinics and receps to doc-cli-rec
         * join doc-cli-rec and meds
         * join doc-cli-rec-meds with diagnosis
         *
         *
         */


        //TODO Your solution
        return streamsBuilder.build();
    }

    @Data
    @AllArgsConstructor
    private class DoctorClinic {
        private Doctor doctor;
        private Clinic clinic;
    }
}
