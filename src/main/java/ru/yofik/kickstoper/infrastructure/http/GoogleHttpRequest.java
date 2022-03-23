package ru.yofik.kickstoper.infrastructure.http;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

@Service
public class GoogleHttpRequest implements HttpRequest{
    private static final Logger LOGGER = Logger.getLogger(GoogleHttpRequest.class.getName());


    private static final HttpRequestFactory HTTP_REQUEST_FACTORY;
    private static final Gson GSON = new Gson();


    static  {
        GsonFactory jsonFactory = new GsonFactory();
        HTTP_REQUEST_FACTORY = new NetHttpTransport().createRequestFactory(
                (com.google.api.client.http.HttpRequest request) -> request.setParser(new JsonObjectParser(jsonFactory))
        );
    }


    private com.google.api.client.http.HttpRequest httpRequest;

    @Override
    public GoogleHttpRequest withBearerAuth(String token) {
        httpRequest.getHeaders().setAuthorization("Bearer " + token);
        return this;
    }

    @SneakyThrows
    @Override
    public GoogleHttpRequest postRequest(String url, Object contentToJson) {
        String content = GSON.toJson(contentToJson);

        LOGGER.info(content);

        httpRequest = HTTP_REQUEST_FACTORY
                .buildPostRequest(
                        new GenericUrl(url),
                        ByteArrayContent.fromString(
                                "application/json",
                                content)
                );
        httpRequest.getHeaders().setContentType("application/json");
        httpRequest.getHeaders().setContentLength((long) content.length());

        return this;
    }

    @SneakyThrows
    @Override
    public HttpRequest postRequest(String url) {
        httpRequest = HTTP_REQUEST_FACTORY
                .buildPostRequest(
                        new GenericUrl(url),
                        null
                );

        return this;
    }

    @SneakyThrows
    @Override
    public GoogleHttpRequest getRequest(String url) {
        httpRequest = HTTP_REQUEST_FACTORY.buildGetRequest(new GenericUrl(url));

        return this;
    }

    @SneakyThrows
    @Override
    public HttpRequest putRequest(String url, Object contentToJson) {
        String content = GSON.toJson(contentToJson);

        httpRequest = HTTP_REQUEST_FACTORY
                .buildPutRequest(
                        new GenericUrl(url),
                        ByteArrayContent.fromString(
                                "application/json",
                                content)
                );
        httpRequest.getHeaders().setContentType("application/json");
        httpRequest.getHeaders().setContentLength((long) content.length());

        return this;
    }

    @SneakyThrows
    @Override
    public HttpRequest deleteRequest(String url) {
        httpRequest = HTTP_REQUEST_FACTORY.buildDeleteRequest(new GenericUrl(url));

        return this;
    }

    @SneakyThrows
    @Override
    public HttpResponse execute() {
        httpRequest.setThrowExceptionOnExecuteError(false);
        com.google.api.client.http.HttpResponse httpResponse = httpRequest.execute();

        StringBuilder content = new StringBuilder();

        if (httpResponse.getContent() != null) {
            Scanner scanner = new Scanner(httpResponse.getContent());

            while (scanner.hasNext()) {
                content.append(scanner.nextLine());
                content.append(System.lineSeparator());
            }

            String contentString;
            if (httpRequest.getContent() != null) {
                contentString = getContentAsString(httpRequest);
            } else {
                contentString = "none";
            }

            LOGGER.info("Executed request: " + httpRequest.getRequestMethod() + " " + httpRequest.getUrl().toURL() + System.lineSeparator() +
                    "Content is: " + contentString + System.lineSeparator() +
                    "Response is: " + httpResponse.getStatusCode() + " content: " + content);
        } else {
            String contentString = getContentAsString(httpRequest);

            LOGGER.info("Executed request: " + httpRequest.getRequestMethod() + " " + httpRequest.getUrl().toURL() + System.lineSeparator() +
                    "Content is: " + contentString + System.lineSeparator() +
                    "Response is: " + httpResponse.getStatusCode() + " content: no content");
        }

        return new HttpResponse(httpResponse, content.toString());
    }

    private String getContentAsString(com.google.api.client.http.HttpRequest httpRequest) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpContent httpContent = httpRequest.getContent();
        httpContent.writeTo(outputStream);
        byte[] bytes = outputStream.toByteArray();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
