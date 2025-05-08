package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class BbangzipAuthException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BbangzipAuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public abstract HttpStatus getStatus(); // 상태 코드는 하위 클래스에서 결정
}
