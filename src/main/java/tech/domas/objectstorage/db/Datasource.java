package tech.domas.objectstorage.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@Getter
public class Datasource {
    private static final Logger LOGGER = LoggerFactory.getLogger(Datasource.class.getName());

    private Connection con;

    public Datasource() {
        try {
            final String sqliteFileName = ConfigCache.configCache.get(Config.SQLITE_FILE_NAME);
            final String sqliteUser = ConfigCache.configCache.get(Config.SQLITE_FILE_NAME);
            final String sqlitePass = ConfigCache.configCache.get(Config.SQLITE_FILE_NAME);
            con = DriverManager.getConnection("jdbc:sqlite: " + sqliteFileName + ".db", sqliteUser, sqlitePass);
        } catch (SQLException e) {
            LOGGER.error("Failed to init sqlite database connection.", e);
        } catch (ExecutionException e) {
            LOGGER.error("Could not read setting from cache.", e);
        }
    }

}
