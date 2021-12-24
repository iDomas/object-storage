package tech.domas.objectstorage.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class FileResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResponse.class.getName());

    private String fileName;
    private String message;

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(new FileResponse(fileName, message));
        } catch (JsonProcessingException e) {
           LOGGER.error("Could not map " + FileResponse.class.getName() + " to JSON.", e);
        }

        return "{}";
    }

}
