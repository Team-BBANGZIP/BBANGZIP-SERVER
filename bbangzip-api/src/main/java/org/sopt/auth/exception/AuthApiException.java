package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

public abstract class AuthApiException extends BbangzipBaseException {

    private final ErrorCode errorCode;

    protected AuthApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}