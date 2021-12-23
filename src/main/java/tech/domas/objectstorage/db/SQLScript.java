package tech.domas.objectstorage.db;

public class SQLScript {

    static String createFileItemString()  {
        return "CREATE TABLE IF NOT EXISTS file_item (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "file_name VARCHAR(255) NOT NULL," +
                    "timestamp BIGINT NOT NULL," +
                    "file_extension VARCHAR(10) NOT NULL," +
                    "mime_type VARCHAR(255) NOT NULL" +
                ");";
    }

    static String createTagForFileName() {
        return "CREATE INDEX IF NOT EXISTS tag_file_name ON file_item (file_name);";
    }

    static String insertIntoFileItem(String fileName, long timestamp, String fileExtension, String mimeType) {
        return "INSERT INTO file_item (file_name, timestamp, file_extension, mime_type) " +
                "VALUES ('" + fileName + "' , " + timestamp + ", '" + fileExtension + "', '" + mimeType + "');";
    }

    static String getFileMimeType(String fileName) {
        return "SELECT mime_type FROM file_item WHERE file_name = '" + fileName + "';";
    }

    static String deleteFile(String fileName) {
        return "DELETE FROM file_item WHERE file_name = '" + fileName + "';";
    }

}
