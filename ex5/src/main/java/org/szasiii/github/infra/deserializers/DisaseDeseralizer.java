package org.szasiii.github.infra.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.szasiii.github.infra.model.Disease;

import java.util.Map;

public class DisaseDeseralizer implements Deserializer<Disease> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Disease deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        Disease data;
        try {
            data = objectMapper.readValue(bytes, Disease.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}