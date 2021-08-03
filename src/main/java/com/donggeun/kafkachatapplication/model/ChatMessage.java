package com.donggeun.kafkachatapplication.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Builder
public class ChatMessage {
    long messageId;
    long roomId;
    //userId까지는 만들지 말자
    long userId = -1;
    String username;

    String content;

    Date createdDateTime;
    Date updatedDateTime;

    public long getUserId() {
        return -1;
    }
}