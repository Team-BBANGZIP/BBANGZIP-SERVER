package org.sopt.category.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

public abstract class CategoryBaseException extends BbangzipBaseException {
    protected CategoryBaseException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected CategoryBaseException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}