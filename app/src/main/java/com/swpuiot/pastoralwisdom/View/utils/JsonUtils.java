package com.swpuiot.pastoralwisdom.View.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by 羊荣毅_L on 2017/1/5.
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";
    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJson(Object o) {
        String s = null;
        try {
            s = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "writeValueAsString failed");
        }
        return s;
    }


    public static <T> T fromJson(String s, Class<T> v) {
        T t = null;
        try {
            t = objectMapper.readValue(s, v);
        } catch (IOException e) {
            Log.d(TAG, "readValue failed");
        }
        return t;
    }
}
