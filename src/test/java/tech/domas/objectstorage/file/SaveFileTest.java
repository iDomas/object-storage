package tech.domas.objectstorage.file;

import org.junit.Test;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;


public class SaveFileTest {


    @Test()
    public void testWriteFile()  {
        ConfigCache.configCache.put(Config.STORAGE_PATH, "./");
        final String content = "Hello World!";
        final String fileExtension = "txt";
        assertTrue(SaveFile.createAndSaveFile(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), fileExtension));
    }
}
