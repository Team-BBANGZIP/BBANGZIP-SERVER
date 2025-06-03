package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryDuplicatedException extends BaseException {

    public CategoryDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
