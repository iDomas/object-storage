package tech.domas.objectstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.ConfigReader;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.util.concurrent.ExecutionException;

public class ObjectStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorage.class.getName());

    public ObjectStorage() {
        // read configurations
        new ConfigReader();
        try {
            LOGGER.info("Test setting is: " + ConfigCache.INSTANCE.configCache.get(Config.TEST_CONFIG));
        } catch (ExecutionException e) {
            LOGGER.error("Failed to read config: " + Config.TEST_CONFIG, e);
        }
    }

}
