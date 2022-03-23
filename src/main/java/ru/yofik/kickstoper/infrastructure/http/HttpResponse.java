package ru.yofik.kickstoper.infrastructure.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.logging.Logger;

public final class HttpResponse {
    private static final Logger LOGGER = Logger.getLogger(HttpResponse.class.getName());


    private static final Gson GSON = new Gson();


    private final com.google.api.client.http.HttpResponse httpResponse;
    private final String content;


    public HttpResponse(com.google.api.client.http.HttpResponse httpResponse, String content) {
        this.content = content;
        this.httpResponse = httpResponse;
    }


    public int getStatusCode() {
        return httpResponse.getStatusCode();
    }

    public boolean isOk() {
        return 200 == httpResponse.getStatusCode();
    }

    public boolean isUnauthenticated() {
        return 401 == httpResponse.getStatusCode();
    }

    public boolean isForbidden() {
        return 403 == httpResponse.getStatusCode();
    }

    public boolean isBadRequest() {
        return 400 == httpResponse.getStatusCode();
    }

    public boolean isServerError() {
        return 500 == httpResponse.getStatusCode();
    }

    public boolean isNotFound() {
        return 404 == httpResponse.hashCode();
    }

    @SneakyThrows
    public <T> T getContent(Class<T> classT) {
        try {
            return GSON.fromJson(content, classT);
        } catch (Throwable t) {
            LOGGER.info("Error: " + t.getMessage());

            Constructor<T> constructor = classT.getConstructor();
            return constructor.newInstance();
        }
    }

    @SneakyThrows
    public <T> T getContent(TypeToken<T> typeToken) {
        try {
            if (content == null) {
                return (T) Collections.emptyList();
            }

            return GSON.fromJson(content, typeToken.getType());
        } catch (Throwable t) {
            LOGGER.info("Error: " + t.getMessage());
            throw new RuntimeException(t);
        }
    }
}
