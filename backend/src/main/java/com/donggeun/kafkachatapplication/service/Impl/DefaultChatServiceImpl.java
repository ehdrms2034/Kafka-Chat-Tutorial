package com.donggeun.kafkachatapplication.service.Impl;

import com.donggeun.kafkachatapplication.config.SocketConfiguration;
import com.donggeun.kafkachatapplication.model.BusinessException;
import com.donggeun.kafkachatapplication.model.ChatMessage;
import com.donggeun.kafkachatapplication.model.ChatRoom;
import com.donggeun.kafkachatapplication.model.ErrCode;
import com.donggeun.kafkachatapplication.service.ChatService;
import com.donggeun.kafkachatapplication.service.JacksonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultChatServiceImpl implements ChatService {

    private final Map<Long, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();
    private final Map<Long, List<ChatMessage>> chatMessages = new ConcurrentHashMap<>();

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AtomicLong roomIndex = new AtomicLong(0);

    @PostConstruct
    public void init() {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(1L)
                .title("테스트")
                .description("테스트방")
                .build();
        chatRoomMap.put(1L, chatRoom);
    }

    @Override
    public void makeRoom(String title, String description) {
        ChatRoom newChatRoom = ChatRoom.builder()
                .roomId(roomIndex.getAndSet(roomIndex.get() + 1))
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
    public List<ChatRoom> getRooms() {
        return new ArrayList<>(chatRoomMap.values());
    }

    @Override
    public void sendMessage(long roomId, long userId, String username, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .messageId(0L)
                .roomId(roomId)
                .userId(userId)
                .content(content)
                .updatedDateTime(new Date())
                .createdDateTime(new Date())
                .username(username)
                .build();

        String jsonFromMessage = JacksonUtil.objectToJson(chatMessage);
        simpMessagingTemplate.convertAndSend(SocketConfiguration.TOPIC_PREFIX + "/chat/" + roomId, jsonFromMessage);

        log.info("[send] chat message 전송 room ID : {}, user ID : {}", roomId, userId);
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

        // TODO: 2021/08/04 채팅방 퇴장 메시지 

    }
}
