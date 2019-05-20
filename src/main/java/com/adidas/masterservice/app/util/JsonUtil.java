package com.adidas.masterservice.app.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.exception.CommandActionExecutionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {

    public static String asJsonString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new CommandActionExecutionException(e);
        }
    }

    //TODO needs to test it
    public static <T> T convertJsonToPOJO(String jsonString, Class<?> target) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, objectMapper .getTypeFactory().constructCollectionType(List.class, Class.forName(target.getName())));
    }
}
