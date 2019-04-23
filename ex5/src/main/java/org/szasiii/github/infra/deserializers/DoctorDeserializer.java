package org.szasiii.github.infra.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.szasiii.github.infra.model.Doctor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DoctorDeserializer implements Deserializer<Doctor> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Doctor deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        Doctor data;
        try {
            data = objectMapper.readValue(bytes, Doctor.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}