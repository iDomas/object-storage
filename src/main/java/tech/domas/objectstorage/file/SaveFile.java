package tech.domas.objectstorage.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class SaveFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveFile.class.getName());

    public static boolean createAndSaveFile(InputStream content, String fileExtension) {
        try {
            if (content.available() == 0) {
                return false;
            }
            final String path = ConfigCache.configCache.get(Config.STORAGE_PATH);
            // TODO: somewhere here add insertion into sqlite
            final String fileName = generateFileName(path, fileExtension);
            File target = new File(fileName);
            FileUtils.copyInputStreamToFile(content, target);
        } catch (IOException e) {
            LOGGER.info("Failed to read input stream. ", e);
        } catch (ExecutionException e) {
            LOGGER.error("Failed to get config from cache.", e);
        }

        return true;
    }

    private static String generateFileName(String path, String fileExtension) {
        if (path.endsWith("/")) {
            return  path + UUID.randomUUID()+ "." + fileExtension;
        } else {
            return path + "/" + UUID.randomUUID()+ "." + fileExtension;
        }
    }
}
