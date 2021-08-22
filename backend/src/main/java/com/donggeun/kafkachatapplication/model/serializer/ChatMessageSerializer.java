package com.donggeun.kafkachatapplication.model.serializer;

import com.donggeun.kafkachatapplication.model.BusinessException;
import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.ErrCode;
import com.donggeun.kafkachatapplication.service.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ChatMessageSerializer implements Serializer<ChatMessage> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, ChatMessage data) {
        return JacksonUtil.objectToBytes(data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ChatMessage data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
