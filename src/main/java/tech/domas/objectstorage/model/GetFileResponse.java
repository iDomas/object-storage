package tech.domas.objectstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

import java.io.FileInputStream;

@AllArgsConstructor
@Getter
public class GetFileResponse {

    private MediaType mimeType;
    private FileInputStream fis;

}
