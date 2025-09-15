package org.sopt.todo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoCoreErrorCode implements ErrorCode {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, 40406, "투두를 찾을 수 없습니다."),
    INVALID_TODO_CONTENT(HttpStatus.BAD_REQUEST, 40013, "투두 내용은 0자일 수 없습니다.")
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
