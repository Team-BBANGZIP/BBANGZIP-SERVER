package org.sopt.user.exception;

import org.sopt.code.ErrorCode;

public class InvalidProfileImageKeyException extends UserCoreException {
    public InvalidProfileImageKeyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static InvalidProfileImageKeyException of(int key) {
        return new InvalidProfileImageKeyException(UserCoreErrorCode.INVALID_PROFILE_IMAGE_KEY);
    }
}