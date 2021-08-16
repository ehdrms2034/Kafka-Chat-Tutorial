package com.donggeun.kafkachatapplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponse {

    int code;
    String message;
    Object result;

    public static DefaultResponse ok(Object result){

        return DefaultResponse.builder()
                .code(0)
                .message("api에 대한 요청에 대한 결과입니다.")
                .result(result)
                .build();

    }

    public static DefaultResponse ok(){

        return DefaultResponse.builder()
                .code(0)
                .message("api에 대한 요청에 대한 결과입니다.")
                .result(null)
                .build();

    }
}
