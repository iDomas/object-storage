package tech.domas.objectstorage.file;

import fi.iki.elonen.NanoFileUpload;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tika.Tika;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;
import tech.domas.objectstorage.db.SQLiteHandlerFactory;
import tech.domas.objectstorage.httpserver.utils.exception.MimeTypeNotSupportedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class SaveFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFile.class.getName());

    private static final String OK = "OK";
    private static final String FAILED_TO_GET_CONFIG = "Failed to get config from cache.";
    private static final String FAILED_TO_PARSE = "Failed to parse file upload request";
    private static final String FAILED_TO_WRITE_FILE = "Failed to write file...";
    private static final String MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES = "Mime type not provided in supported mime types";

    public static SaveFileResult saveFile(NanoHTTPD.IHTTPSession session, String fileExtension) {
        String fileName = "";
        try {
            List<FileItem> files = new NanoFileUpload(new DiskFileItemFactory()).parseRequest(session);
            fileName = writeFile(files.get(0).get(), fileExtension);
            return new SaveFileResult(true, fileName, OK, NanoHTTPD.Response.Status.OK);
        } catch (ExecutionException e) {
            LOGGER.error(FAILED_TO_GET_CONFIG, e);
            return new SaveFileResult(false, fileName, FAILED_TO_GET_CONFIG, NanoHTTPD.Response.Status.INTERNAL_ERROR);
        } catch (FileUploadException e) {
            LOGGER.error(FAILED_TO_PARSE, e);
            return new SaveFileResult(false, fileName, FAILED_TO_PARSE, NanoHTTPD.Response.Status.INTERNAL_ERROR);
        } catch (IOException e) {
            LOGGER.error(FAILED_TO_WRITE_FILE, e);
            return new SaveFileResult(false, fileName, FAILED_TO_WRITE_FILE, NanoHTTPD.Response.Status.INTERNAL_ERROR);
        } catch (MimeTypeNotSupportedException e) {
            LOGGER.error(MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES, e);
            return new SaveFileResult(false, fileName, MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES,
                    NanoHTTPD.Response.Status.INTERNAL_ERROR);
        }
    }

    private static String writeFile(byte[] fileContent, String fileExtension) throws ExecutionException, IOException, MimeTypeNotSupportedException {
        final String mimeType = (new Tika()).detect(fileContent);

        String[] supportedMimeTypes = ConfigCache.configCache.get(Config.MIME_TYPE_SUPPORT).split(",");
        if (!Arrays.asList(supportedMimeTypes).contains(mimeType)) {
            throw new MimeTypeNotSupportedException();
        }

        final String path = ConfigCache.configCache.get(Config.STORAGE_PATH);
        String fullFileName = UUID.randomUUID() + "." + fileExtension;
        SQLiteHandlerFactory.getSqLiteHandler().insertIntoFileItem(fullFileName, DateTime.now(), fileExtension, mimeType);
        final String fileName = filePath(path, fullFileName);
        Files.write(Paths.get(fileName), fileContent);
        return fullFileName;
    }

    private static String filePath(String path, String fullFileName) {
        if (path.endsWith("/")) {
            return  path + fullFileName;
        } else {
            return path + "/" + fullFileName;
        }
    }
}
