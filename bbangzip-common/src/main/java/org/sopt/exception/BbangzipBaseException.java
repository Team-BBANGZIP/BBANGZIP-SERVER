package org.sopt.exception;

import org.sopt.code.ErrorCode;

public abstract class BbangzipBaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public BbangzipBaseException(ErrorCode errorCode) {
        super(errorCode != null ? errorCode.getMessage() : null);
        this.errorCode = errorCode;
    }

    public BbangzipBaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BbangzipBaseException(String message) {
        super(message);
        this.errorCode = null;
    }

    public BbangzipBaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public BbangzipBaseException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
