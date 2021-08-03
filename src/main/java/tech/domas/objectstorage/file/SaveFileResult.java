package tech.domas.objectstorage.file;

import fi.iki.elonen.NanoHTTPD;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveFileResult {

    private boolean isSaved;
    private String fileName;
    private String message;
    private NanoHTTPD.Response.Status status;

}
