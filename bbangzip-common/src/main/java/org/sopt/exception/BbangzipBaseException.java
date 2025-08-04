package org.sopt.exception;

import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class BbangzipBaseException extends RuntimeException {
    private final ErrorCode errorCode;

    protected BbangzipBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected BbangzipBaseException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public abstract HttpStatus getStatus();  // 하위 클래스에서 명시적으로 지정
}
