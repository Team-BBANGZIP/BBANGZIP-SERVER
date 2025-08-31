package org.sopt.category.exception;

import org.sopt.code.ErrorCode;

public class CategoryInvalidColorException extends CategoryCoreException {
    public CategoryInvalidColorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
