package tech.domas.objectstorage.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.domas.objectstorage.ObjectStorage;

public class ObjectStorageApp
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorageApp.class.getName());

    public static void main( String[] args ) {
        LOGGER.info("Starting application...");
        if (args.length > 0) {
                new ObjectStorage(args[0]);
        } else {
            new ObjectStorage();
        }

//        HttpClientCredentials credentials = new HttpClientCredentials();
//        credentials.invokePost(
//                ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_USERNAME),
//                ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_PASSWORD));
    }
}
