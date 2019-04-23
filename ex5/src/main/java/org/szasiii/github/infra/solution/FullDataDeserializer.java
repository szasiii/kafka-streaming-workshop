package org.szasiii.github.infra.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class FullDataDeserializer implements Deserializer<FullData> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public FullData deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        FullData data;
        try {
            data = objectMapper.readValue(bytes, FullData.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}