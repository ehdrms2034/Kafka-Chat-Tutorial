package com.donggeun.kafkachatapplication.service;

import com.donggeun.kafkachatapplication.model.ChatRoom;

import java.util.List;

public interface ChatService {

    void makeRoom(String title, String description);

    void joinRoom(long roomId, long userId);

    List<ChatRoom> getRooms();

    void sendMessage(long roomId, long userId, String username, String content);

    List<String> getRoomUsers(long roomId);

    void leaveRoom(long roomId, long userId);

}
