package tech.domas.objectstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.ConfigReader;
import tech.domas.objectstorage.config.cache.ConfigCache;
import tech.domas.objectstorage.httpserver.NanoServer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ObjectStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorage.class.getName());

    public ObjectStorage() {
        // read configurations
        new ConfigReader();

        // start http server
        try {
            new NanoServer();
        } catch (IOException e) {
            LOGGER.error("Problems starting server. ", e);
        } catch (ExecutionException e) {
            LOGGER.error("Problems reading configurations from cache.", e);
        }
    }

}
