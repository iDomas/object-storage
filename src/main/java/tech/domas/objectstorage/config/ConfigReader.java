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
            ConfigCache.INSTANCE.configCache.put(Config.NANO_HTTPD_SERVER_PORT, config.getString(Config.NANO_HTTPD_SERVER_PORT));
            ConfigCache.INSTANCE.configCache.put(Config.NANO_HTTPD_SERVER_DAEMON, config.getString(Config.NANO_HTTPD_SERVER_DAEMON));
            ConfigCache.INSTANCE.configCache.put(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT, config.getString(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT));
        } catch (ConfigurationException e) {
            LOGGER.error("Not successful read of config. ", e);
        }
    }

}
