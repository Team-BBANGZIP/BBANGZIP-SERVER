package org.sopt.todo.exception;

public class TodoNotFoundException extends TodoCoreException {

    public TodoNotFoundException(TodoCoreErrorCode errorCode) {
        super(errorCode);
    }

    public TodoNotFoundException(String detailMessage) {
        super(TodoCoreErrorCode.TODO_NOT_FOUND, detailMessage);
    }
}