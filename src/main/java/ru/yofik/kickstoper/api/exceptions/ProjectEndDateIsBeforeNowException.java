package ru.yofik.kickstoper.api.exceptions;

public class ProjectEndDateIsBeforeNowException extends ResponseOnException {
    public ProjectEndDateIsBeforeNowException() {
        super(ResponseCode.PROJECT_END_DATE_IS_BEFORE_NOW, "Project end date in applicationDto has to be after the current date.");
    }
}
