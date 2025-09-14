package org.sopt.todo.exception;

import org.sopt.code.ErrorCode;

public class TodoNotFoundException extends TodoCoreException {

    public TodoNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}