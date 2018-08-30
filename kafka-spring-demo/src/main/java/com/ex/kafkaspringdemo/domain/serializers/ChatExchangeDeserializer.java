package com.ex.kafkaspringdemo.domain.serializers;

import com.ex.kafkaspringdemo.domain.ChatExchange;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ChatExchangeDeserializer implements Deserializer<ChatExchange>{
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ChatExchange deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        ChatExchange exchange = null;

        try {
            exchange = mapper.readValue(data, ChatExchange.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchange;
    }

    @Override
    public void close() {

    }
}
