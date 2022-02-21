package ru.yofik.kickstoper.api.exceptions;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final JsonSerializer<ResponseOnException> SERIALIZER = (src, typeOfSrc, context) -> {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("code", new JsonPrimitive(src.getCode().getCode()));
        jsonObject.add("description", new JsonPrimitive(src.getDescription()));
        return jsonObject;
    };
    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(ResponseOnException.class, SERIALIZER)
            .setPrettyPrinting()
            .create();


    @ExceptionHandler(value = {ResponseOnException.class})
    public ResponseEntity<?> handle(ResponseOnException exception, WebRequest webRequest) {
        if (exception.getCode().getCode() >= 4000 && exception.getCode().getCode() < 5000) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.BAD_REQUEST);
        }

        if (exception.getCode().getCode() >= 5000) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}