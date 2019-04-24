package org.szasiii.github.kstreams.solution;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.junit.Assert;
import org.junit.Test;
import org.szasiii.github.kstreams.Ex2App;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ex2AppTest {
    @Test
    public void test() {
        Ex2App ex2App = new Ex2App();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, UUID.randomUUID().toString());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        TopologyTestDriver testDriver = new TopologyTestDriver(ex2App.createTopology(), config);

        ConsumerRecordFactory<String, String> factoryStream = new ConsumerRecordFactory<>("ex2-input", new StringSerializer(), new StringSerializer());

        provideTestData().forEach(longStringKeyValue -> {
            testDriver.pipeInput(factoryStream.create("ex2-input", longStringKeyValue.key, longStringKeyValue.value));
        });

        List<KeyValue<String, Long>> legacy = IntStream.range(0, 100)
                .mapToObj(i -> testDriver.readOutput("ex2-legacy-output", new StringDeserializer(), new LongDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());

        List<KeyValue<String, Long>> newData = IntStream.range(0, 100)
                .mapToObj(i -> testDriver.readOutput("ex2-new-output", new StringDeserializer(), new LongDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());


        Assert.assertEquals(resultsLegacy(), legacy);
        Assert.assertEquals(resultsNew(), newData);

    }

    private List<KeyValue<String, String>> provideTestData() {
        List<KeyValue<String, String>> result = new ArrayList<>();
        result.add(KeyValue.pair("new", "bambi:2,4,5,1,3,4,5,-1,23,-11"));
        result.add(KeyValue.pair("new", "batman:2,3"));
        result.add(KeyValue.pair("new", "optimus:1"));
        result.add(KeyValue.pair("new", "godzilla:1000,-20,420"));
        result.add(KeyValue.pair("new", "fake:0"));
        result.add(KeyValue.pair("legacy", "bambi:2,4,5,10,3,4,5,-10,23,-11"));
        result.add(KeyValue.pair("legacy", "batman:2,3"));
        result.add(KeyValue.pair("legacy", "optimus:100"));
        result.add(KeyValue.pair("legacy", "godzilla:1,-20,420"));
        result.add(KeyValue.pair("legacy", "fake:0"));
        return result;
    }

    private List<KeyValue<String, Long>> resultsLegacy() {
        List<KeyValue<String, Long>> result = new ArrayList<>();
        result.add(KeyValue.pair("bambi", 2L));
        result.add(KeyValue.pair("bambi", 6L));
        result.add(KeyValue.pair("bambi", 11L));
        result.add(KeyValue.pair("bambi", 21L));
        result.add(KeyValue.pair("bambi", 24L));
        result.add(KeyValue.pair("bambi", 28L));
        result.add(KeyValue.pair("bambi", 33L));
        result.add(KeyValue.pair("bambi", 23L));
        result.add(KeyValue.pair("bambi", 46L));
        result.add(KeyValue.pair("bambi", 35L));
        result.add(KeyValue.pair("optimus", 100L));
        result.add(KeyValue.pair("godzilla", 1L));
        result.add(KeyValue.pair("godzilla", -19L));
        result.add(KeyValue.pair("godzilla", 401L));
        return result;
    }

    private List<KeyValue<String, Long>> resultsNew() {
        List<KeyValue<String, Long>> result = new ArrayList<>();
        result.add(KeyValue.pair("bambi", 2L));
        result.add(KeyValue.pair("bambi", 6L));
        result.add(KeyValue.pair("bambi", 11L));
        result.add(KeyValue.pair("bambi", 12L));
        result.add(KeyValue.pair("bambi", 15L));
        result.add(KeyValue.pair("bambi", 19L));
        result.add(KeyValue.pair("bambi", 24L));
        result.add(KeyValue.pair("bambi", 23L));
        result.add(KeyValue.pair("bambi", 46L));
        result.add(KeyValue.pair("bambi", 35L));
        result.add(KeyValue.pair("optimus", 1L));
        result.add(KeyValue.pair("godzilla", 1000L));
        result.add(KeyValue.pair("godzilla", 980L));
        result.add(KeyValue.pair("godzilla", 1400L));
        return result;
    }
}
