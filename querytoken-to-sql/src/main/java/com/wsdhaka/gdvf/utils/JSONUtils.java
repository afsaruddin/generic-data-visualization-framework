package com.wsdhaka.gdvf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JSONUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T fromJson(String json, Class<T> klass) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, klass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
