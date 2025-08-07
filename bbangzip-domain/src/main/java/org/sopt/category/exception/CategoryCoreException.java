package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

public abstract class CategoryCoreException extends BbangzipBaseException {
    private final ErrorCode errorCode;

    protected CategoryCoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected CategoryCoreException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
