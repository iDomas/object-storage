package tech.domas.objectstorage.file;

import org.apache.tika.Tika;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tech.domas.objectstorage.db.SQLiteHandler;
import tech.domas.objectstorage.exception.MimeTypeNotSupportedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class SaveFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFile.class.getName());

    private static final String OK = "OK";
    private static final String FAILED_TO_GET_CONFIG = "Failed to get config from cache.";
    private static final String FAILED_TO_WRITE_FILE = "Failed to write file...";
    private static final String MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES = "Mime type not provided in supported mime types";

    @Autowired
    private SQLiteHandler sqLiteHandler;

    private String mimetypesSupport;
    private String storagePath;

    public SaveFile(@Value("${app.mimetype.support}") String mimetypesSupport, @Value("${app.storage.path}") String storagePath) {
        this.mimetypesSupport = mimetypesSupport;
        this.storagePath = storagePath;
    }

    public SaveFileResult saveFile(MultipartFile file, String fileExtension) {
        String fileName = "";
        try {
            fileName = writeFile(file.getBytes(), fileExtension);
            return new SaveFileResult(true, fileName, OK, HttpStatus.OK);
        } catch (ExecutionException e) {
            LOGGER.error(FAILED_TO_GET_CONFIG, e);
            return new SaveFileResult(false, fileName, FAILED_TO_GET_CONFIG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOGGER.error(FAILED_TO_WRITE_FILE, e);
            return new SaveFileResult(false, fileName, FAILED_TO_WRITE_FILE, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MimeTypeNotSupportedException e) {
            LOGGER.error(MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES, e);
            return new SaveFileResult(false, fileName, MIME_TYPE_NOT_PROVIDED_IN_SUPPORTED_MIME_TYPES,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String writeFile(byte[] fileContent, String fileExtension) throws ExecutionException, IOException {
        final String mimeType = (new Tika()).detect(fileContent);

        String[] supportedMimeTypes = mimetypesSupport.split(",");
        if (!Arrays.asList(supportedMimeTypes).contains(mimeType)) {
            throw new MimeTypeNotSupportedException();
        }

        String fullFileName = UUID.randomUUID() + "." + fileExtension;
        sqLiteHandler.insertIntoFileItem(fullFileName, DateTime.now(), fileExtension, mimeType);
        final String fileName = filePath(storagePath, fullFileName);
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
