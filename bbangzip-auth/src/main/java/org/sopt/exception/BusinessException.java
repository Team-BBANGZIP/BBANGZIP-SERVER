package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class BusinessException extends AuthBaseException{
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}