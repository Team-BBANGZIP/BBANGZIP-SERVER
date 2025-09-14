package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class TokenNotFoundException extends AuthBaseException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
