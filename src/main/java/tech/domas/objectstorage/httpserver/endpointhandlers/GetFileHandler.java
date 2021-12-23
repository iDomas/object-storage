package tech.domas.objectstorage.httpserver.endpointhandlers;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.db.SQLiteHandlerFactory;
import tech.domas.objectstorage.file.ServeFile;
import tech.domas.objectstorage.httpserver.utils.AbstractParameterWrapper;
import tech.domas.objectstorage.httpserver.utils.ErrorResponse;
import tech.domas.objectstorage.httpserver.utils.constants.HttpRequestError;

import java.io.FileInputStream;
import java.io.IOException;

public class GetFileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetFileHandler.class.getName());

    private static final String FILE_NAME = "fileName";

    private static final String FAILED_READ_FILE = "Failed read file";


    public static NanoHTTPD.Response handleGetFile(NanoHTTPD.IHTTPSession session) {
        LOGGER.debug("Starting get file...");

        AbstractParameterWrapper parameterWrapper = AbstractParameterHandler.
                getParameter(session, FILE_NAME,
                        HttpRequestError.FILE_NAME_REQUIRED,
                        HttpRequestError.FILE_NAME_IS_BLANK,
                        HttpRequestError.CAN_GET_ONLY_ONE_FILE);
        if (!parameterWrapper.isOk()) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(parameterWrapper.getErrorMessage()));
        }
        final String fileName = parameterWrapper.getParameterValue();

        final FileInputStream fis = ServeFile.serveFile(fileName);
        if (fis == null) {
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(FAILED_READ_FILE));
        }
        final String mimeType = SQLiteHandlerFactory.getSqLiteHandler().getFileMimeType(fileName.split("\\.")[0]);
        try {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, mimeType, fis, fis.available());
        } catch (IOException e) {
            LOGGER.error(FAILED_READ_FILE, e);
            return ErrorHandler.handlerError(session, ErrorResponse.toJSON(FAILED_READ_FILE));
        }
    }
}
