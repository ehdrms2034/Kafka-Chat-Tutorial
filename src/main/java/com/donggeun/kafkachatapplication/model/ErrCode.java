package com.donggeun.kafkachatapplication.model;

public enum ErrCode {
    NOT_EXIST_CHATROOM(101, "chatting room not exists.");

    private int errCode;
    private String message;

    ErrCode(int errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }
}
