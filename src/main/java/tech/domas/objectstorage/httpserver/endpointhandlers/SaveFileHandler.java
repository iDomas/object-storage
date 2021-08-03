package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.file.SaveFile;
import tech.domas.objectstorage.file.SaveFileResult;
import tech.domas.objectstorage.httpserver.utils.AbstractParameterWrapper;
import tech.domas.objectstorage.httpserver.utils.ErrorResponse;
import tech.domas.objectstorage.httpserver.utils.SaveFileRequest;
import tech.domas.objectstorage.httpserver.utils.FileResponse;

import java.io.IOException;

public class SaveFileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFileHandler.class.getName());

    private static final String FILE_EXTENSION = "fileExtension";

    private static final String NO_REQUIRED_KEY = "No required key...";
    private static final String NO_VALUE = "No required value...";
    private static final String FILE_EXTENSION_TO_LONG = "File extension to long...";
    private static final String NO_CONTENT = "No content...";
    private static final String TOO_MUCH_EXTENSIONS = "Too much extensions...";
    private static final String ERROR_READING_INPUT_STREAM = "Error reading input stream...";


    public static NanoHTTPD.Response handleSaveFile(NanoHTTPD.IHTTPSession session) {
        LOGGER.debug("Starting save file... ");
        AbstractParameterWrapper parameterWrapper = AbstractParameterHandler.
                getParameter(session, FILE_EXTENSION, NO_REQUIRED_KEY, NO_VALUE, TOO_MUCH_EXTENSIONS);
        if (!parameterWrapper.isOk()) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(parameterWrapper.getErrorMessage()));
        }
        final String fileExtension = parameterWrapper.getParameterValue();

        SaveFileRequest request = new SaveFileRequest(fileExtension);
        if (StringUtils.isBlank(request.getFileExtension())) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(NO_VALUE));
        }

        if (request.getFileExtension().length() > 10) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(FILE_EXTENSION_TO_LONG));
        }

        try {
            if (session.getInputStream().available() == 0) {
                return ErrorHandler.handlerError(session, ErrorResponse.toJSON(NO_CONTENT));
            }
        } catch (IOException e) {
            LOGGER.error(ERROR_READING_INPUT_STREAM, e);
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(ERROR_READING_INPUT_STREAM));
        }

        final SaveFileResult result = SaveFile.saveFile(session, request.getFileExtension());
        return NanoHTTPD.newFixedLengthResponse(result.getStatus(), NanoHTTPD.MIME_PLAINTEXT, FileResponse.toJSON(result.getFileName(), result.getMessage()));
    }

}
