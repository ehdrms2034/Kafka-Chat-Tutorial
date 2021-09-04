package com.donggeun.kafkachatapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ChatMessage {

    long messageId;
    @JsonProperty("roomId")
    long roomId;
    //userId까지는 만들지 말자
    @JsonProperty("userId")
    long userId = -1;
    @JsonProperty("username")
    String username;

    @JsonProperty("content")
    String content;

    @JsonProperty("createdDateTime")
    Date createdDateTime;
    @JsonProperty("updatedDateTime")
    Date updatedDateTime;

    public long getUserId() {
        return -1;
    }

    @JsonCreator
    public ChatMessage(
            @JsonProperty("messageId") long messageId,
            @JsonProperty("roomId") long roomId,
            @JsonProperty("userId") long userId,
            @JsonProperty("username") String username,
            @JsonProperty("content") String content,
            @JsonProperty("createdDateTime") long createdDateTime,
            @JsonProperty("updatedDateTime") long updatedDateTime) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdDateTime = new Date(createdDateTime);
        this.updatedDateTime = new Date(updatedDateTime);
    }
}