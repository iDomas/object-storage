package tech.domas.objectstorage.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class ServeFile {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServeFile.class.getName());

    private String storagePath;

    public ServeFile(@Value("${app.storage.path}") String storagePath) {
        this.storagePath = storagePath;
    }

    public FileInputStream serveFile(String fileName) {
        try {
            final String fullFilePath = fileName.startsWith("/") ? storagePath + fileName : storagePath + "/" + fileName;
            if (new File(fullFilePath).exists()) {
                return new FileInputStream(fullFilePath);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not read file...", e);
        }

        return null;
    }

}
