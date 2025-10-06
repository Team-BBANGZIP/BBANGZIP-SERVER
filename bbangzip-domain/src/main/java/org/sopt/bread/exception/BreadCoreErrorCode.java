package org.sopt.bread.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BreadCoreErrorCode implements ErrorCode {

    // 404
    BREAD_NOT_FOUND(HttpStatus.NOT_FOUND,40017, "해당 Bread 데이터가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}