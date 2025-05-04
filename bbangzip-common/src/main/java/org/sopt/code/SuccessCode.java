package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum SuccessCode implements ApiCode {
    OK(HttpStatus.OK, 20000,"요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED, 20100, "요청이 성공했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT,20400,"요청이 성공했습니다.")
    ;

    public final HttpStatus httpStatus;
    private final int code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}