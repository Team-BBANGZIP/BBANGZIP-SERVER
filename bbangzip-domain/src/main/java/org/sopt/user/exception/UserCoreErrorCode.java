package org.sopt.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserCoreErrorCode implements ErrorCode {

    // 400
    INVALID_PROFILE_IMAGE_KEY(HttpStatus.BAD_REQUEST,40014, "잘못된 profileImageKey 입니다."),
    INVALID_REGISTER_STATUS_TYPE(HttpStatus.BAD_REQUEST, 40415, "올바르지 않은 가입 타입입니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 40401, "사용자를 찾을 수 없습니다.");

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
