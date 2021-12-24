package tech.domas.objectstorage.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class SaveFileResult {

    private boolean isSaved;
    private String fileName;
    private String message;
    private HttpStatus status;

}
