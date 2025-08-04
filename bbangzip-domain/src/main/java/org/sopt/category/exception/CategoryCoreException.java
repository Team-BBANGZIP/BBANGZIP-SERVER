package org.sopt.category.exception;


import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class CategoryCoreException extends CategoryBaseException {
    protected CategoryCoreException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected CategoryCoreException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR; // Core 에러는 서버 측 에러로 간주
    }
}