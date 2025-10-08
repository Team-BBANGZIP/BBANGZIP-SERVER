package org.sopt.todo.exception;

import org.sopt.code.ErrorCode;

public class TodoCategoryColorMismatchException extends TodoCoreException {
    public TodoCategoryColorMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}