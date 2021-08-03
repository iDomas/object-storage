package tech.domas.objectstorage.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitTables {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitTables.class.getName());

    InitTables(Connection conn) {

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(SQLScript.createFileItemString());
            stmt.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL script.", e);
        }
    }

}
