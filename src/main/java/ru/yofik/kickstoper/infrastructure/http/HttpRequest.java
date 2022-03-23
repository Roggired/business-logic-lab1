package ru.yofik.kickstoper.infrastructure.http;

import org.springframework.stereotype.Service;

@Service
public interface HttpRequest {
    HttpRequest withBearerAuth(String token);
    HttpRequest postRequest(String url, Object contentToJson);
    HttpRequest postRequest(String url);
    HttpRequest getRequest(String url);
    HttpRequest putRequest(String url, Object contentToJson);
    HttpRequest deleteRequest(String url);
    HttpResponse execute();
}
