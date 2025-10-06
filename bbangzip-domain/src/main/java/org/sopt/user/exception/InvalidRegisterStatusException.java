package org.sopt.user.exception;

import org.sopt.code.ErrorCode;

public class InvalidRegisterStatusException extends UserCoreException {

    public InvalidRegisterStatusException(ErrorCode errorCode) {
        super(errorCode);
    }

}