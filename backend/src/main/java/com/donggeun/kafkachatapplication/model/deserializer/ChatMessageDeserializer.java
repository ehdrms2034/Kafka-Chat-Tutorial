package com.donggeun.kafkachatapplication.model.deserializer;

import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.service.JacksonUtil;
import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;

public class ChatMessageDeserializer implements Deserializer<ChatMessage> {

    @Override
    public ChatMessage deserialize(InputStream inputStream) throws IOException {
        return JacksonUtil.bytesToObject(inputStream.readAllBytes());
    }

    @Override
    public ChatMessage deserializeFromByteArray(byte[] serialized) throws IOException {
        return JacksonUtil.bytesToObject(serialized);
    }
}
