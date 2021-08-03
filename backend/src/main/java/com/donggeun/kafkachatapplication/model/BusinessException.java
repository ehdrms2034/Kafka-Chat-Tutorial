package com.donggeun.kafkachatapplication.model;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private ErrCode errCode;

    public BusinessException(ErrCode errCode) {
        this.errCode = errCode;
    }
}
