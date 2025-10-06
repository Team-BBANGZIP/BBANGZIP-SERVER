package org.sopt.bread.exception;

import lombok.Getter;
import org.sopt.code.ErrorCode;
import org.sopt.exception.BbangzipBaseException;

@Getter
public class BreadCoreException extends BbangzipBaseException {
    private final ErrorCode errorCode;

    protected BreadCoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}