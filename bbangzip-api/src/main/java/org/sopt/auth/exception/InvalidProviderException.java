package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;

public class InvalidProviderException extends AuthApiException{

    public InvalidProviderException(ErrorCode errorCode) {
        super(errorCode);
    }

}