package org.sopt.category.exception;

import org.sopt.code.ErrorCode;

public class CategoryInvalidOrderException extends CategoryCoreException {
    public CategoryInvalidOrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
