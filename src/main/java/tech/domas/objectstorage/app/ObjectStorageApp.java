package tech.domas.objectstorage.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.ObjectStorage;

public class ObjectStorageApp
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorageApp.class.getName());

    public static void main( String[] args )
    {
       LOGGER.info("Starting application...");
       new ObjectStorage();
    }
}
