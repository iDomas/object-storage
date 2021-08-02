package tech.domas.objectstorage.db;

import org.apache.commons.dbutils.DbUtils;

public class SQLiteHandler {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";

    public SQLiteHandler() {
        Datasource ds = new Datasource();

        DbUtils.loadDriver(JDBC_DRIVER);
    }

}
