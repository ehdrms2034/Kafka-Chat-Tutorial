package com.donggeun.kafkachatapplication.controller;

import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.ChatRoom;
import com.donggeun.kafkachatapplication.model.DefaultResponse;
import com.donggeun.kafkachatapplication.model.requestparam.CreateRoomDTO;
import com.donggeun.kafkachatapplication.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@MessageMapping("/socket/chat")
@RequiredArgsConstructor
public class ChatController {

    public final ChatService chatService;

    @PostMapping("/rooms")
    public DefaultResponse createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        chatService.makeRoom(createRoomDTO.getTitle(), createRoomDTO.getDescription());
        return DefaultResponse.ok();
    }

    @GetMapping("/rooms")
    public DefaultResponse getRooms() {
        List<ChatRoom> chatRooms = chatService.getRooms();
        return DefaultResponse.ok(chatRooms);
    }

    @MessageMapping("/send")
    public void sendMessage(ChatMessage chatMessage) {
        chatService.sendMessage(
                chatMessage.getRoomId(),
                chatMessage.getUserId(),
                chatMessage.getUsername(),
                chatMessage.getContent()
        );
    }


}