package tech.domas.objectstorage.db;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteHandler.class.getName());

    private static final String UNKNOWN_MIME_TYPE = "unknown";

    private final Datasource ds;

    public SQLiteHandler() {
        ds = new Datasource();

        new InitTables(ds.getCon());
    }

    public boolean insertIntoFileItem(String fileName, DateTime dateTime, String fileExtension, String mimeType) {
        try {
            Statement stmt = ds.getCon().createStatement();
            stmt.execute(SQLScript.insertIntoFileItem(fileName, dateTime.getMillis(), fileExtension, mimeType));
            stmt.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL script.", e);
        }
        return false;
    }

    public void closeDbConnection() {
        try {
            ds.getCon().close();
            LOGGER.info("Db connection closed.");
        } catch (SQLException e) {
            LOGGER.error("Could not close DB connection.", e);
        }
    }

    public String getFileMimeType(String fileName) {
        try {
            Statement stmt = ds.getCon().createStatement();
            stmt.execute(SQLScript.getFileMimeType(fileName));
            ResultSet result = stmt.getResultSet();
            String mimeType = UNKNOWN_MIME_TYPE;
            while (result.next()) {
                if (result.isLast()) {
                    final int mimeTypeColumn = 4;
                    mimeType = result.getString(mimeTypeColumn);
                }
            }
            result.close();
            stmt.close();
            return mimeType;
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL script.", e);
        }

        return UNKNOWN_MIME_TYPE;
    }
}
