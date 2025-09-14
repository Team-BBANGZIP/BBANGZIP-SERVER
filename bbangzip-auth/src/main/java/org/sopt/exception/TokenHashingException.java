package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class TokenHashingException extends AuthBaseException{
    public TokenHashingException(ErrorCode errorCode) {
        super(errorCode);
    }
}