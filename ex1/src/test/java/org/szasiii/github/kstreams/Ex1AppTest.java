package org.szasiii.github.kstreams;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ex1AppTest {

    @Test
    public void test() {
        Ex1App ex1App = new Ex1App();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        TopologyTestDriver testDriver = new TopologyTestDriver(ex1App.createTopology(), config);

        ConsumerRecordFactory<Long, String> factoryStream = new ConsumerRecordFactory<>("ex1-stream-input", new LongSerializer(), new StringSerializer());
        ConsumerRecordFactory<Long, String> factoryTable = new ConsumerRecordFactory<>("ex1-table-input", new LongSerializer(), new StringSerializer());

        testDriver.pipeInput(factoryStream.create(1L, "test"));
        testDriver.pipeInput(factoryStream.create(2L, "test"));
        testDriver.pipeInput(factoryStream.create(3L, "test"));
        testDriver.pipeInput(factoryStream.create(1L, "test-update"));
        testDriver.pipeInput(factoryStream.create(2L, null));

        testDriver.pipeInput(factoryTable.create(1L, "test"));
        testDriver.pipeInput(factoryTable.create(2L, "test"));
        testDriver.pipeInput(factoryTable.create(3L, "test"));
        testDriver.pipeInput(factoryTable.create(1L, "test-update"));
        testDriver.pipeInput(factoryTable.create(2L, null));

        List<KeyValue<Long, String>> resultTable = IntStream.range(0, 1000)
                .mapToObj(i -> testDriver.readOutput("ex1-table-input", new LongDeserializer(), new StringDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());

        List<KeyValue<Long, String>> resultStream = IntStream.range(0, 1000)
                .mapToObj(i -> testDriver.readOutput("ex1-stream-output", new LongDeserializer(), new StringDeserializer()))
                .filter(Objects::nonNull)
                .map(v -> KeyValue.pair(v.key(), v.value()))
                .collect(Collectors.toList());

        Assert.assertSame(Collections.emptyList(), resultTable);
        Assert.assertSame(Collections.emptyList(), resultStream);

    }

}