package org.sopt.category.exception;

import org.sopt.code.ErrorCode;

public class CategoryNotFoundException extends CategoryCoreException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
