package ru.yofik.kickstoper.context.application.exception;

import ru.yofik.kickstoper.api.exceptions.ResponseCode;
import ru.yofik.kickstoper.api.exceptions.ResponseOnException;

public class ProjectNameIsNotFreeException extends ResponseOnException {
    public ProjectNameIsNotFreeException() {
        super(ResponseCode.PROJECT_NAME_IS_NOT_FREE, "Project with such name already exists");
    }
}
