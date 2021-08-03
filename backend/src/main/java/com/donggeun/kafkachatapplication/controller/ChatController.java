package com.donggeun.kafkachatapplication.controller;

import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    public final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(ChatMessage chatMessage) {
        chatService.sendMessage(
                chatMessage.getRoomId(),
                chatMessage.getUserId(),
                chatMessage.getContent()
        );
    }


}