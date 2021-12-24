package tech.domas.objectstorage.httpclient;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class HttpClientCredentials {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CLIENT_ID = "client_id";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_SECRET = "client_secret";

    @Value("${keycloak.auth.endpoint}")
    private String keycloakEndpoint;

    @Value("${keycloak.auth.client-id}")
    private String keycloakAuthClientId;

    @Value("${keycloak.auth.grant-type}")
    private String keycloakGrantType;

    @Value("${keycloak.auth.client-secret}")
    private String keycloakClientSecret;

    public void invokePost(String username, String password) {
        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody body = prepareBody(username, password);
            Request request = new Request.Builder()
                    .addHeader("Accept", "application/x-www-form-urlencoded")
                    .url(keycloakEndpoint)
                    .method("POST", body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println(response);
                System.out.println(response.body().string());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private RequestBody prepareBody(String username, String password) {
        return new FormBody.Builder()
                .add(USERNAME, username)
                .add(PASSWORD, password)
                .add(CLIENT_ID, keycloakAuthClientId)
                .add(GRANT_TYPE, keycloakGrantType)
                .add(CLIENT_SECRET, keycloakClientSecret)
                .build();
    }

}
