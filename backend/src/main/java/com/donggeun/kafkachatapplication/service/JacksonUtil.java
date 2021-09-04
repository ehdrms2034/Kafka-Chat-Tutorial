package com.donggeun.kafkachatapplication.service;

import com.donggeun.kafkachatapplication.model.BusinessException;
import com.donggeun.kafkachatapplication.model.ErrCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //Object를 Json string 형태로 변환한다.
    public static String objectToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrCode.JSON_PARSING_ERROR);
        }
    }

    public static byte[] objectToBytes(Object object){
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e){
            throw new BusinessException(ErrCode.JSON_PARSING_ERROR);
        }
    }

    public static <T> T bytesToObject(byte[] bytes, Class<T> toClass){
        try{
            Object object = objectMapper.readValue(bytes, Object.class);

            return objectMapper.readValue(bytes, toClass);
        } catch(JsonProcessingException e){
            throw new BusinessException(ErrCode.JSON_PARSING_ERROR);
        } catch (IOException e) {
            throw new BusinessException(ErrCode.JSON_IO_EXCEPTION);
        }
    }
}
