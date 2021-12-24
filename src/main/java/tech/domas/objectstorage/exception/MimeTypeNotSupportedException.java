package tech.domas.objectstorage.exception;

public class MimeTypeNotSupportedException extends RuntimeException {

    public MimeTypeNotSupportedException() {
        super("Mime type not supported.");
    }

}