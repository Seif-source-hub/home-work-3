package ru.topacademy.deserializer;

import ru.topacademy.domain.Symbol;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class SymbolDeserializer implements Deserializer<Symbol> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Symbol deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, Symbol.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON to Symbol", e);
        }
    }

    @Override
    public void close() {
    }
}