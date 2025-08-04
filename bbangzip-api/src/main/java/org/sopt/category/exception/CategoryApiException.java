package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class CategoryApiException extends CategoryBaseException {
    protected CategoryApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected CategoryApiException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
