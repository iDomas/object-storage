package tech.domas.objectstorage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObjectStorageApp
{
    public static void main( String[] args ) {
        SpringApplication.run(ObjectStorageApp.class, args);

//        HttpClientCredentials credentials = new HttpClientCredentials();
//        credentials.invokePost(
//                ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_USERNAME),
//                ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_PASSWORD));
    }
}
