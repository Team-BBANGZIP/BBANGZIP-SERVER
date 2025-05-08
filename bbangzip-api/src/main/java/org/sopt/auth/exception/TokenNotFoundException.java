package org.sopt.auth.exception;

import lombok.Getter;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class TokenNotFoundException extends BbangzipAuthException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}