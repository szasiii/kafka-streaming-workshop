package org.szasiii.github.infra.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.szasiii.github.infra.model.Medicine;

import java.util.Map;

public class MedicineDeserializer implements Deserializer<Medicine> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Medicine deserialize(String s, byte[] bytes) {
        if (bytes == null)
            return null;

        Medicine data;
        try {
            data = objectMapper.readValue(bytes, Medicine.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}