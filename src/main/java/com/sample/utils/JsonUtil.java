package com.sample.utils;

import static java.util.Collections.emptyMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

    private static final String RESPONSE_PATH = "src/test/resources/json/";
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = getObjectMapperInstance();
    public static final String EMPTY = "";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static <T> T jsonFileToObject(String jsonFileName, Class<T> destinationType) throws IOException {
        final File btMockResponse = new File(RESPONSE_PATH + jsonFileName);

        return OBJECT_MAPPER.readValue(btMockResponse, destinationType);
    }

    private static ObjectMapper getObjectMapperInstance() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static String toJsonString(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.warn("Exception on json converting.", e);
        }

        return EMPTY;
    }

    public static String toJsonPrettyString(Object object) {

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.warn("Exception on json converting.", e);
        }

        return EMPTY;
    }

    public static <T> T toObject(String jsonString, final Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.warn("Exception on json converting.", e);
        }

        return null;
    }

    public static boolean isJsonValid(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return false;
        }

        try {
            objectMapper.readTree(jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String extend(Object... obj) {
        Map<String, Object> rootMap = new HashMap<>();
        for (Object o : obj) {
            Map map = Optional.ofNullable(toObject(toJsonString(o), Map.class)).orElse(emptyMap());
            rootMap = deepMerge(rootMap, map);
        }

        return toJsonString(rootMap);
    }

    /**
     * Merge the contents of two or more objects together into the first object.
     *
     * @param original  The object to extend. It will receive the new properties.
     * @param newMap An object containing additional properties to merge in.
     * @return
     */
    public static Map deepMerge(Map original, Map newMap) {
        for (Object key : newMap.keySet()) {
            Object ov = original.get(key);
            Object nv = newMap.get(key);

            if (nv instanceof Map && ov instanceof Map) {
                Map originalChild = (Map) ov;
                Map newChild = (Map) nv;
                original.put(key, deepMerge(originalChild, newChild));
            } else {
                original.put(key, nv);
            }
        }

        return original;
    }
}
