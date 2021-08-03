package tech.domas.objectstorage.config;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.cache.ConfigCache;


public class ConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class.getName());

    public ConfigReader(String pathToConfigFile) {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(pathToConfigFile);
            ConfigCache.configCache.put(Config.NANO_HTTPD_SERVER_PORT, config.getString(Config.NANO_HTTPD_SERVER_PORT));
            ConfigCache.configCache.put(Config.NANO_HTTPD_SERVER_DAEMON, config.getString(Config.NANO_HTTPD_SERVER_DAEMON));
            ConfigCache.configCache.put(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT, config.getString(Config.NANO_HTTPD_SERVER_SOCKET_READ_TIMEOUT));
            ConfigCache.configCache.put(Config.STORAGE_PATH, config.getString(Config.STORAGE_PATH));
            ConfigCache.configCache.put(Config.SQLITE_FILE_NAME, config.getString(Config.SQLITE_FILE_NAME));
            ConfigCache.configCache.put(Config.SQLITE_USER, config.getString(Config.SQLITE_USER));
            ConfigCache.configCache.put(Config.SQLITE_PASS, config.getString(Config.SQLITE_PASS));
            ConfigCache.configCache.put(Config.MIME_TYPE_SUPPORT, config.getString(Config.MIME_TYPE_SUPPORT));
        } catch (ConfigurationException e) {
            LOGGER.error("Not successful read of config. ", e);
        }
    }

}
