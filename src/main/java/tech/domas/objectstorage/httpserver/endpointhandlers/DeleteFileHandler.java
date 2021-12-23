package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;
import tech.domas.objectstorage.db.SQLiteHandlerFactory;
import tech.domas.objectstorage.httpserver.utils.AbstractParameterWrapper;
import tech.domas.objectstorage.httpserver.utils.ErrorResponse;
import tech.domas.objectstorage.httpserver.utils.FileResponse;
import tech.domas.objectstorage.httpserver.utils.constants.HttpRequestError;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class DeleteFileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFileHandler.class.getName());

    private static final String FILE_NAME = "fileName";
    private final static String FAILED_DELETE_FILE = "Failed to delete file";

    public static NanoHTTPD.Response handleDeleteFile(NanoHTTPD.IHTTPSession session) {
        LOGGER.info("Starting delete file...");
        AbstractParameterWrapper parameterWrapper = AbstractParameterHandler.
                getParameter(session, FILE_NAME,
                        HttpRequestError.FILE_NAME_REQUIRED,
                        HttpRequestError.FILE_NAME_IS_BLANK,
                        HttpRequestError.CAN_GET_ONLY_ONE_FILE);

        if (!parameterWrapper.isOk()) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(parameterWrapper.getErrorMessage()));
        }

        final String fileName = parameterWrapper.getParameterValue();

        boolean isDeletedFromStorage = false;

        try {
            File file = new File(ConfigCache.configCache.get(Config.STORAGE_PATH) + "/" + fileName);
            isDeletedFromStorage = file.delete();
            LOGGER.info("File " + fileName + " deleted from storage");
        } catch (ExecutionException ex) {
            LOGGER.error("Failed to read config", ex);
        }

        boolean isDeleted = SQLiteHandlerFactory.getSqLiteHandler().deleteFile(fileName.split("\\.")[0]);
        if (isDeletedFromStorage && isDeleted) {
            LOGGER.info("File " + fileName + " deleted from db");
            return NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.OK,
                    NanoHTTPD.MIME_PLAINTEXT,
                    FileResponse.toJSON(fileName, "OK"));
        } else {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(FAILED_DELETE_FILE));
        }
    }

}
