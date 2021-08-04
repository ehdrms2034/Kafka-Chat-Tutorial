package com.donggeun.kafkachatapplication.model;

public enum ErrCode {
    NOT_EXIST_CHATROOM(101, "chatting room not exists."),
    JSON_PARSING_ERROR(102, "json parsing error");

    private int errCode;
    private String message;

    ErrCode(int errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }
}
