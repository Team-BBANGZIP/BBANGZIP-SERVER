package org.sopt.user.exception;

import org.sopt.code.ErrorCode;

public class UserNotFoundException extends UserCoreException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
