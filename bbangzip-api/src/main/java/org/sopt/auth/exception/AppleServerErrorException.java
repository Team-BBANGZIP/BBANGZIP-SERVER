package org.sopt.auth.exception;

import org.sopt.code.ErrorCode;

public class AppleServerErrorException extends AuthApiException{
    public AppleServerErrorException(ErrorCode errorCode) {
        super(errorCode);
    }
}