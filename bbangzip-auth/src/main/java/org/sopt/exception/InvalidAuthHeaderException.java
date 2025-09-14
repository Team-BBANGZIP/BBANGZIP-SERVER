package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class InvalidAuthHeaderException extends AuthBaseException{
    public InvalidAuthHeaderException(ErrorCode errorCode) {
        super(errorCode);
    }
}