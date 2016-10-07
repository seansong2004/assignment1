package com.paypal.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by seansong on 10/6/16.
 */
public class ObjectMappers {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper get() {
        return mapper;
    }
}
