package tech.domas.objectstorage.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Component
public class Datasource {
    private static final Logger LOGGER = LoggerFactory.getLogger(Datasource.class.getName());

    private Connection con;

    public Datasource(
            @Value( "${app.sqlite.file.name}" ) String sqliteFileName,
            @Value( "${app.sqlite.user}" ) String sqliteUser,
            @Value( "${app.sqlite.user}" ) String sqlitePass) {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + sqliteFileName + ".db", sqliteUser, sqlitePass);
        } catch (SQLException e) {
            LOGGER.error("Failed to init sqlite database connection.", e);
        }
    }

}
