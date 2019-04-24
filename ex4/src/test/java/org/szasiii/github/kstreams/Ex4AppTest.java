package org.szasiii.github.kstreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.Test;

import java.util.Properties;
import java.util.UUID;

public class Ex4AppTest {

    @Test
    public void test() {
        Ex4App ex4App = new Ex4App();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, UUID.randomUUID().toString());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        TopologyTestDriver testDriver = new TopologyTestDriver(ex4App.createTopology(), config);


    }

}