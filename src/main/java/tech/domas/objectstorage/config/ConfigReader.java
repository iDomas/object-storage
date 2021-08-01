package tech.domas.objectstorage.config;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.File;

public class ConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class.getName());

    public ConfigReader() {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("system.properties"));
            ConfigCache.INSTANCE.configCache.put(Config.TEST_CONFIG, config.getString(Config.TEST_CONFIG));
        } catch (ConfigurationException e) {
            LOGGER.error("Not successful read of config. ", e);
        }
    }

}
