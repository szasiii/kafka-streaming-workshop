package org.szasiii.github.infra.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.szasiii.github.infra.model.Prescription;

import java.util.Map;

public class PrescriptionDeserializer implements Deserializer<Prescription> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Prescription deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        Prescription data;
        try {
            data = objectMapper.readValue(bytes, Prescription.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}