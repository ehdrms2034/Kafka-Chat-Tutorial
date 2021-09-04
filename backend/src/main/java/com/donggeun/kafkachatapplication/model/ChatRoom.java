package com.donggeun.kafkachatapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ChatRoom {
    long roomId;
    long ownerId;

    String title;
    String description;

    int currentParticipant;

    Date createdDateTime;
    Date updatedDateTime;
}
