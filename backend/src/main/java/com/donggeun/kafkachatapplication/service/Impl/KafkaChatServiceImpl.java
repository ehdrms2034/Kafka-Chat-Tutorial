package com.donggeun.kafkachatapplication.service.Impl;

import com.donggeun.kafkachatapplication.config.KafkaProducerConfiguration;
import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.ChatRoom;
import com.donggeun.kafkachatapplication.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class KafkaChatServiceImpl implements ChatService {

    private final ChatService chatService;
    private final KafkaTemplate<String, ChatMessage> chatMessageKafkaTemplate;

    @Autowired
    public KafkaChatServiceImpl(
            @Qualifier("defaultChatServiceImpl") ChatService chatService,
            KafkaTemplate<String, ChatMessage> chatMessageKafkaTemplate
    ) {
        this.chatService = chatService;
        this.chatMessageKafkaTemplate = chatMessageKafkaTemplate;
    }

    @Override
    public void makeRoom(String title, String description) {
        chatService.makeRoom(title, description);
    }

    @Override
    public void joinRoom(long roomId, long userId) {
        chatService.joinRoom(roomId, userId);
    }

    @Override
    public List<ChatRoom> getRooms() {
        return chatService.getRooms();
    }

    @Override
    public void sendMessage(long roomId, long userId, String username, String content) {
        ChatMessage newChatMessage = ChatMessage.builder()
                .userId(userId)
                .roomId(roomId)
                .messageId(0L)
                .username(username)
                .content(content)
                .createdDateTime(new Date())
                .updatedDateTime(new Date())
                .build();

        ListenableFuture<SendResult<String, ChatMessage>> messageRequest
                = chatMessageKafkaTemplate.send(KafkaProducerConfiguration.CHAT_TOPIC_NAME, newChatMessage);

        messageRequest.addCallback((result) -> {
            log.info("send message by kafka : {}", result.getProducerRecord().value());
        }, (failure) -> {
            log.info("fail send message by kafka : {}", failure.getCause());
        });
    }

    @KafkaListener(topics = KafkaProducerConfiguration.CHAT_TOPIC_NAME,
            id = KafkaProducerConfiguration.CHAT_TOPIC_NAME,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenMessageFromKafka(ConsumerRecord<String, ChatMessage> consumerRecord, @Payload ChatMessage listenedMessage) {
        chatService.sendMessage(listenedMessage.getRoomId(), listenedMessage.getUserId(), listenedMessage.getUsername(), listenedMessage.getContent());
    }

    @Override
    public List<String> getRoomUsers(long roomId) {
        return chatService.getRoomUsers(roomId);
    }

    @Override
    public void leaveRoom(long roomId, long userId) {
        chatService.leaveRoom(roomId, userId);
    }
}
