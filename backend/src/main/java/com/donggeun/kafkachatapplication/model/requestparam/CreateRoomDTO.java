package com.donggeun.kafkachatapplication.model.requestparam;

import lombok.Data;

@Data
public class CreateRoomDTO {
    String title;
    String description;
}
