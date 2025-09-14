package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class InvalidTokenException extends AuthBaseException{
    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}