package tech.domas.objectstorage.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class ServeFile {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServeFile.class.getName());

    public static FileInputStream serveFile(String fileName) {
        try {
            final String path = ConfigCache.configCache.get(Config.STORAGE_PATH);
            final String fullFilePath = fileName.startsWith("/") ? path + fileName : path + "/" + fileName;
            if (new File(fullFilePath).exists()) {
                return new FileInputStream(fullFilePath);
            } else {
                return null;
            }
        } catch (ExecutionException e) {
            LOGGER.error("Could not read config...", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not read file...", e);
        }

        return null;
    }

}
