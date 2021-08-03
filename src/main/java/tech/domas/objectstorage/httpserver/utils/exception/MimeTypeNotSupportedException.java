package tech.domas.objectstorage.httpserver.utils.exception;

public class MimeTypeNotSupportedException extends RuntimeException {

    public MimeTypeNotSupportedException() {
        super("Mime type not supported.");
    }

}
