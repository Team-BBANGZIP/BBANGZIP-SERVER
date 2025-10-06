package org.sopt.bread.exception;

import org.sopt.code.ErrorCode;

public class BreadNotFoundException extends BreadCoreException {
    public BreadNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}