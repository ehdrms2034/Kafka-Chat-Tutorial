package com.donggeun.kafkachatapplication.service.Impl;

import com.donggeun.kafkachatapplication.model.BusinessException;
import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.ChatRoom;
import com.donggeun.kafkachatapplication.model.ErrCode;
import com.donggeun.kafkachatapplication.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class DefaultChatServiceImpl implements ChatService {

    private final Map<Long, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AtomicLong roomIndex = new AtomicLong(0);

    @Override
    public void makeRoom(String title, String description) {
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomId(roomIndex.getAndSet(roomIndex.get()+1))
                .title(title)
                .description(description)
                .currentParticipant(0)
                .updatedDateTime(new Date())
                .createdDateTime(new Date())
                .build();

        chatRoomMap.put(newChatRoom.getRoomId(), newChatRoom);
    }

    @Override
    public void joinRoom(long roomId, long userId) {
        if (!chatRoomMap.containsKey(roomId)) {
            throw new BusinessException(ErrCode.NOT_EXIST_CHATROOM);
        }



        //소켓 연결 완료시
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        chatRoom.setCurrentParticipant(chatRoom.getCurrentParticipant() + 1);

        // TODO: 2021/08/04 채팅방 입장 메시지

    }

    @Override
    public void sendMessage(long roomId, long userId, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .messageId(0L)
                .roomId(roomId)
                .userId(userId)
                .content(content)
                .updatedDateTime(new Date())
                .createdDateTime(new Date())
                .username("유저")
                .build();

        simpMessagingTemplate.convertAndSend(Long.toString(roomId), chatMessage);
    }

    @Override
    public List<String> getRoomUsers(long roomId) {
        return null;
    }

    @Override
    public void leaveRoom(long roomId, long userId) {
        if (!chatRoomMap.containsKey(roomId)) {
            throw new BusinessException(ErrCode.NOT_EXIST_CHATROOM);
        }


    }
}
