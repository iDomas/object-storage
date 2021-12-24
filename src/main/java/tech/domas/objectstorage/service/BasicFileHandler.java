package tech.domas.objectstorage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.domas.objectstorage.db.SQLiteHandler;
import tech.domas.objectstorage.file.SaveFile;
import tech.domas.objectstorage.file.SaveFileResult;
import tech.domas.objectstorage.file.ServeFile;
import tech.domas.objectstorage.model.FileResponse;
import tech.domas.objectstorage.model.GetFileResponse;

import java.io.File;
import java.io.FileInputStream;

@Service
public class BasicFileHandler implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicFileHandler.class.getName());

    @Autowired
    private SaveFile saveFile;
    @Autowired
    private ServeFile serveFile;
    @Autowired
    private SQLiteHandler sqLiteHandler;

    private String storagePath;

    public BasicFileHandler(@Value("${app.storage.path}") String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public GetFileResponse getFile(String fileName) {
        final FileInputStream fis = serveFile.serveFile(fileName);
        final String mimeType = sqLiteHandler.getFileMimeType(fileName );
        return new GetFileResponse(MediaType.parseMediaType(mimeType), fis);
    }

    @Override
    public FileResponse saveFile(MultipartFile file, String fileExtension) {
        final SaveFileResult result = saveFile.saveFile(file, fileExtension);
        return new FileResponse(result.getFileName(), "OK");
    }

    @Override
    public FileResponse deleteFile(String fileName) {

        File file = new File(storagePath + "/" + fileName);
        boolean isDeletedFromStorage = file.delete();

        boolean isDeleted = sqLiteHandler.deleteFile(fileName.split("\\.")[0]);
        if (isDeletedFromStorage && isDeleted) {
            LOGGER.info("File " + fileName + " deleted from db");
            return new FileResponse(fileName, "OK");
        } else {
            return new FileResponse(fileName, "Internal server error");
        }
    }


}
