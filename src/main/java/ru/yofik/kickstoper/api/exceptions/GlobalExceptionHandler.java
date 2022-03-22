package ru.yofik.kickstoper.api.exceptions;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
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


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> handle(ConstraintViolationException exception) {
        return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResponseOnException.class})
    public ResponseEntity<?> handle(ResponseOnException exception) {
        if (exception.getCode().getCode() == 4010) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.UNAUTHORIZED);
        }

        if (exception.getCode().getCode() == 4030) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.FORBIDDEN);
        }

        if (exception.getCode().getCode() >= 4000 && exception.getCode().getCode() < 5000) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.BAD_REQUEST);
        }

        if (exception.getCode().getCode() >= 5000) {
            return new ResponseEntity<Object>(GSON.toJson(exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}