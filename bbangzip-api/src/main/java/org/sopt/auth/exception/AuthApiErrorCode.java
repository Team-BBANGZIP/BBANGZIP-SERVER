package org.sopt.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public  enum AuthApiErrorCode implements ErrorCode {

    // 400
    INVALID_PROVIDER(HttpStatus.BAD_REQUEST, 40015, "지원하지 않는 Provider입니다."),

    // 503
    AUTH_APPLE_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, 50300, "애플 로그인에서 에러가 발생했습니다."),
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