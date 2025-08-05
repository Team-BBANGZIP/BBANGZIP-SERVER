package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

public abstract class CategoryApiException extends BbangzipBaseException {
    private final ErrorCode errorCode;

    protected CategoryApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected CategoryApiException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
