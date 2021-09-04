package com.donggeun.kafkachatapplication.model.deserializer;

import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.service.JacksonUtil;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ChatMessageDeserializer implements Deserializer<ChatMessage> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ChatMessage deserialize(String topic, byte[] data) {
        return JacksonUtil.bytesToObject(data, ChatMessage.class);
    }

    @Override
    public ChatMessage deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
