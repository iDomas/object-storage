package tech.domas.objectstorage.db;

public class SQLScript {

    static String createFileItemString()  {
        return "CREATE TABLE IF NOT EXISTS file_item (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "file_name VARCHAR(255) NOT NULL," +
                    "timestamp BIGINT NOT NULL," +
                    "file_extension VARCHAR(10) NOT NULL" +
                ");";
    }

    static String insertIntoFileItem(String fileName, long timestamp, String fileExtension) {
        return "INSERT INTO file_item (file_name, timestamp, file_extension) " +
                "VALUES ('" + fileName + "' , " + timestamp + ", '" + fileExtension + "');";
    }

}
