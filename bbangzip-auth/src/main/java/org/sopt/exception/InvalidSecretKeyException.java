package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class InvalidSecretKeyException extends AuthBaseException{

    public InvalidSecretKeyException(ErrorCode errorCode) {
        super(errorCode);
    }

}