package tech.domas.objectstorage.httpserver.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorResponse.class.getName());

    private String errorMessage;

    public static String toJSON(String errorMessage) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(new ErrorResponse(errorMessage));
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not map " + ErrorResponse.class.getName() + " to JSON.", e);
        }

        return "{}";
    }


}
