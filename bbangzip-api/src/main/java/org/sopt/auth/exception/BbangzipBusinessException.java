package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class BbangzipBusinessException extends BbangzipAuthException {
    public BbangzipBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

