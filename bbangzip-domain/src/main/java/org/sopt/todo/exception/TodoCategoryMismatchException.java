package org.sopt.todo.exception;

import org.sopt.code.ErrorCode;

public class TodoCategoryMismatchException extends TodoCoreException {
    public TodoCategoryMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}