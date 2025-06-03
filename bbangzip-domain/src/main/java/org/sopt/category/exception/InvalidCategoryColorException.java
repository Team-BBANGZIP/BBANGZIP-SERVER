package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidCategoryColorException extends BaseException {

    public InvalidCategoryColorException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
