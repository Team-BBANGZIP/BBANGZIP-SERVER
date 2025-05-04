package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    // 400
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40000, "잘못된 요청 값입니다."),
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, 40400, "요청한 API 엔드포인트가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 40500, "지원하지 않는 HTTP 메서드입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "알 수 없는 서버 내부 오류입니다"),
    ;

    private final HttpStatus status;
    private final int code;
    private final String message;

    GlobalErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
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