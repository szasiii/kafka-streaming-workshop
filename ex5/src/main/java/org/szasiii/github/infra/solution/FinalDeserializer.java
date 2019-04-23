package org.szasiii.github.infra.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.streams.KeyValue;
import org.szasiii.github.infra.model.Disease;

import java.util.Map;

public class FinalDeserializer implements Deserializer<FinalData> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public FinalData deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        FinalData data;
        try {
            data = objectMapper.readValue(bytes, FinalData.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}