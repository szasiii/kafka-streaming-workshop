package org.szasiii.github.infra.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.szasiii.github.infra.model.Clinic;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ClinicDeserializer implements Deserializer<Clinic> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Clinic deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        Clinic data;
        try {
            data = objectMapper.readValue(bytes, Clinic.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}
