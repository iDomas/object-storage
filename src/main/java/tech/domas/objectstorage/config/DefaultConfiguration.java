package tech.domas.objectstorage.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class DefaultConfiguration {

    @Value("${app.storage.path}")
    private String storagePath;

    @Value("${app.sqlite.file.name}")
    private String sqliteFileName;
    @Value("${app.sqlite.user}")
    private String sqliteUser;
    @Value("${app.sqlite.pass}")
    private String sqlitePass;

    @Value("${app.mimetype.support}")
    private String mimetypeSupport;

    @Value("${app.keycloak.auth.username}")
    private String keycloakAuthUsername;
    @Value("${app.keycloak.auth.password}")
    private String keycloakAuthPassword;
    @Value("${app.keycloak.auth.endpoint}")
    private String keycloakAuthEndpoint;
    @Value("${app.keycloak.auth.client-id}")
    private String keycloakAuthClientId;
    @Value("${app.keycloak.auth.grant-type}")
    private String keycloakAuthGrantType;
    @Value("${app.keycloak.auth.client-secret}")
    private String keycloakAuthClientSecret;

}
