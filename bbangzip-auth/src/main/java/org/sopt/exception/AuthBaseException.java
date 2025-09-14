package org.sopt.exception;

import org.sopt.code.ErrorCode;

public abstract class AuthBaseException extends BbangzipBaseException {

    private final ErrorCode errorCode;

    protected AuthBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected AuthBaseException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}