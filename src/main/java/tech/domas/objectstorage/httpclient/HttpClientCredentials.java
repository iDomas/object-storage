package tech.domas.objectstorage.httpclient;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.domas.objectstorage.config.Config;
import tech.domas.objectstorage.config.cache.ConfigCache;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HttpClientCredentials {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CLIENT_ID = "client_id";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_SECRET = "client_secret";

    public void invokePost(String username, String password) {
        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody body = prepareBody(username, password);
            Request request = new Request.Builder()
                    .addHeader("Accept", "application/x-www-form-urlencoded")
                    .url(ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_ENDPOINT))
                    .method("POST", body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println(response);
                System.out.println(response.body().string());
            }
        } catch (IOException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }

    }

    private RequestBody prepareBody(String username, String password) {
        try {
            return new FormBody.Builder()
                    .add(USERNAME, username)
                    .add(PASSWORD, password)
                    .add(CLIENT_ID, ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_CLIENT_ID))
                    .add(GRANT_TYPE, ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_GRANT_TYPE))
                    .add(CLIENT_SECRET, ConfigCache.configCache.get(Config.KEYCLOAK_AUTH_CLIENT_SECRET))
                    .build();
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

}
