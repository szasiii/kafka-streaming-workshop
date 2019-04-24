package org.szasiii.github.kstreams.solution;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.junit.Assert;
import org.junit.Test;
import org.szasiii.github.kstreams.Ex3App;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.szasiii.kstreams.Utils.exRecord;

public class Ex3AppTest {

    @Test
    public void test() {
        Ex3App ex3App = new Ex3App();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, UUID.randomUUID().toString());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        TopologyTestDriver testDriver = new TopologyTestDriver(ex3App.createTopology(), config);

        ConsumerRecordFactory<String, String> factoryStream = new ConsumerRecordFactory<>("...", new StringSerializer(), new StringSerializer());

        provideTestData().forEach(producerRecord -> {
            testDriver.pipeInput(factoryStream.create(producerRecord.topic(), producerRecord.key(), producerRecord.value()));
        });

        List<KeyValue<String, String>> innerJoinResult = IntStream.range(0, 1000)
                .mapToObj(i -> testDriver.readOutput("ex3-inner-join-output", new StringDeserializer(), new StringDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());

        List<KeyValue<String, String>> leftJoinResult = IntStream.range(0, 1000)
                .mapToObj(i -> testDriver.readOutput("ex3-left-join-output", new StringDeserializer(), new StringDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());


        Assert.assertEquals(resultsInner(), innerJoinResult);
        Assert.assertEquals(resultsLeft(), leftJoinResult);

    }

    private List<ProducerRecord<String, String>> provideTestData() {
        List<ProducerRecord<String, String>> result = new ArrayList<>();
        result.add(exRecord("ex3-user-table", "bambi", "Name=bambi,Feature=cuteness"));
        result.add(exRecord("ex3-user-orders", "bambi", "Apples and Flowers (1)"));
        result.add(exRecord("ex3-user-orders", "godzilla", "Tokyo (1)"));
        result.add(exRecord("ex3-user-table", "bambi", "Name=bambi,Feature=ultra-cuteness"));
        result.add(exRecord("ex3-user-orders", "bambi", "Flowers (3)"));
        result.add(exRecord("ex3-user-orders", "batman", "Computer (4)"));
        result.add(exRecord("ex3-user-table", "batman", "Name=batman,Feature=money"));
        result.add(exRecord("ex3-user-orders", "batman", "Justice (4)"));
        result.add(exRecord("ex3-user-table", "batman", null));
        result.add(exRecord("ex3-user-table", "ironman", "First=ironman,Feature=more-money"));
        result.add(exRecord("ex3-user-table", "ironman", null));
        result.add(exRecord("ex3-user-orders", "ironman", "Ironsuit (47)"));

        return result;
    }

    private List<KeyValue<String, String>> resultsInner() {
        List<KeyValue<String, String>> result = new ArrayList<>();
        result.add(KeyValue.pair("bambi", "Order=Apples and Flowers (1),UserInfo=[Name=bambi,Feature=cuteness]"));
        result.add(KeyValue.pair("bambi", "Order=Flowers (3),UserInfo=[Name=bambi,Feature=ultra-cuteness]"));
        result.add(KeyValue.pair("batman", "Order=Justice (4),UserInfo=[Name=batman,Feature=money]"));
        return result;
    }

    private List<KeyValue<String, String>> resultsLeft() {
        List<KeyValue<String, String>> result = new ArrayList<>();
        result.add(KeyValue.pair("bambi", "Order=Apples and Flowers (1),UserInfo=[Name=bambi,Feature=cuteness]"));
        result.add(KeyValue.pair("godzilla", "Order=Tokyo (1),UserInfo=null"));
        result.add(KeyValue.pair("bambi", "Order=Flowers (3),UserInfo=[Name=bambi,Feature=ultra-cuteness]"));
        result.add(KeyValue.pair("batman", "Order=Computer (4),UserInfo=null"));
        result.add(KeyValue.pair("batman", "Order=Justice (4),UserInfo=[Name=batman,Feature=money]"));
        result.add(KeyValue.pair("ironman", "Order=Ironsuit (47),UserInfo=null"));
        return result;
    }

}
