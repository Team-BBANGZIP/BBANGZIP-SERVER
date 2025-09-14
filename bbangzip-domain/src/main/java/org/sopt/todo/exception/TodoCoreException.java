package org.sopt.todo.exception;

import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

public abstract class TodoCoreException extends BbangzipBaseException {

    private final ErrorCode errorCode;

    protected TodoCoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected TodoCoreException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
