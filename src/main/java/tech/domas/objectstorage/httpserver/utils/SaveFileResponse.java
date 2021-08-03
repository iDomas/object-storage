package tech.domas.objectstorage.httpserver.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class SaveFileResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFileResponse.class.getName());

    private String fileName;
    private String message;

    public static String toJSON(String fileName, String message) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(new SaveFileResponse(fileName, message));
        } catch (JsonProcessingException e) {
           LOGGER.error("Could not map " + SaveFileResponse.class.getName() + " to JSON.", e);
        }

        return "{}";
    }

}
