package org.sopt.todo.exception;

import org.sopt.code.ErrorCode;

public class InvalidTodoContentException extends TodoCoreException {

    public InvalidTodoContentException(ErrorCode errorCode) {
        super(errorCode);
    }
}