package com.disanbo.component.common.util;

import com.disanbo.component.common.entity.JSONArray;
import com.disanbo.component.common.entity.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author chauncy
 * @date 2018/11/1 10:52
 */

public class JSON {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JavaBean转换为json字符串
     */
    public static String toJSONString(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * json字符串转换为JSONObject
     */
    public static JSONObject parseObject(String text) throws Exception {
        return objectMapper.readValue(text, JSONObject.class);
    }

    /**
     * json字符串转换为JavaBean
     */
    public static <T> T parseObject(String text, Class<T> clazz) throws Exception {
        return objectMapper.readValue(text, clazz);
    }

    /**
     * json字符串转换为JavaBean
     */
    public static <T> T parseObject(String text, TypeReference<T> typeReference) throws Exception {
        return objectMapper.readValue(text, typeReference);
    }

    /**
     * json字符串转换为JavaBean数组
     */
    public static JSONArray parseArray(String text) throws Exception {
        return objectMapper.readValue(text, JSONArray.class);
    }

    /**
     * json字符串转换为JavaBean数组
     */
    public static <T> T parseArray(String text, TypeReference<T> typeReference) throws Exception {
        return objectMapper.readValue(text, typeReference);
    }

}
