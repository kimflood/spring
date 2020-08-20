package com.sample.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final String RESPONSE_PATH = "src/test/resources/json/";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static <T> T jsonFileToObject(String jsonFileName, Class<T> destinationType) throws IOException {
        final File btMockResponse = new File(RESPONSE_PATH + jsonFileName);

        return OBJECT_MAPPER.readValue(btMockResponse, destinationType);
    }
}
