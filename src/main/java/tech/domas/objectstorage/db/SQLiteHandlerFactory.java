package tech.domas.objectstorage.db;

public class SQLiteHandlerFactory {

    private static SQLiteHandler sqLiteHandler;

    public static SQLiteHandler getSqLiteHandler() {
        if (sqLiteHandler == null) {
            sqLiteHandler = new SQLiteHandler();
            return sqLiteHandler;
        }
        return sqLiteHandler;
    }
}
