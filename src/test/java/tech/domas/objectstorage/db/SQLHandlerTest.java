package tech.domas.objectstorage.db;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class SQLHandlerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLHandlerTest.class.getName());

    private SQLiteHandler sqLiteHandler;

    @Before
    public void setUp() {
        ConfigCache.configCache.put(Config.SQLITE_FILE_NAME, "for-tests-only");
        sqLiteHandler = new SQLiteHandler();
    }

    @Test
    public void testInsertIntoFileItem() throws ExecutionException {
        final String fileName = "Test";
        final DateTime dateTime = DateTime.now();
        final String fileExtension = "txt";

        assertTrue(sqLiteHandler.insertIntoFileItem(fileName, dateTime, fileExtension, Mockito.anyString()));

        sqLiteHandler.closeDbConnection();

        final String dbFileName = ConfigCache.configCache.get(Config.SQLITE_FILE_NAME);
        final File db = new File("./" + dbFileName + ".db");
        // TODO currently for some reason not deleting db file. FInd out in the future
        if (db.delete()) {
            LOGGER.info("Test db successfully deleted.");
        } else {
            LOGGER.error("Could not delete test db.");
        }
    }

}
