package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends BbangzipAuthException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }
}