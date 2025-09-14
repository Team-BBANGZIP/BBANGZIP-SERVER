package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class UnAuthorizedException extends AuthBaseException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
