package ru.yofik.kickstoper.api.exceptions;

public class ProjectNameIsNotFreeException extends ResponseOnException{
    public ProjectNameIsNotFreeException() {
        super(ResponseCode.PROJECT_NAME_IS_NOT_FREE, "Project with such name already exists");
    }
}
