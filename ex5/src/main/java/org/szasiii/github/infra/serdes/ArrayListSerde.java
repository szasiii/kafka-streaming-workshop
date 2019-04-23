package org.szasiii.github.infra.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.szasiii.github.infra.deserializers.ArrayListDeserializer;
import org.szasiii.github.infra.serializers.ArrayListSerializer;

import java.util.ArrayList;
import java.util.Map;

public class ArrayListSerde<T> implements Serde<ArrayList<T>> {

    private Serde<ArrayList<T>> inner;

    public ArrayListSerde(Serde<T> serde) {
        inner =
                Serdes.serdeFrom(
                        new ArrayListSerializer<>(serde.serializer()),
                        new ArrayListDeserializer<>(serde.deserializer()));
    }

    public ArrayListSerde() {}

    @Override
    public Serializer<ArrayList<T>> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<ArrayList<T>> deserializer() {
        return inner.deserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.serializer().configure(configs, isKey);
        inner.deserializer().configure(configs, isKey);
    }

    @Override
    public void close() {
        inner.serializer().close();
        inner.deserializer().close();
    }
}

