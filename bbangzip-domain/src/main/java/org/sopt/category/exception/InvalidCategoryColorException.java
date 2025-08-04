package org.sopt.category.exception;

import org.sopt.code.ErrorCode;

public class InvalidCategoryColorException extends CategoryCoreException {
    public InvalidCategoryColorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
