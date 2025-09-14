package org.sopt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    // 400 Bad Request
    WRONG_ENTRY_POINT(HttpStatus.BAD_REQUEST, 40007, "잘못된 요청입니다."),
    MALFORMED_JWT_TOKEN(HttpStatus.BAD_REQUEST, 40008, "잘못된 형식의 토큰입니다."),
    TYPE_ERROR_JWT_TOKEN(HttpStatus.BAD_REQUEST, 40009, "올바른 타입의 JWT 토큰이 아닙니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, 40010, "지원하지 않는 형식의 토큰입니다."),
    INVALID_AUTH_HEADER(HttpStatus.BAD_REQUEST, 40011, "Authorization 헤더 형식이 잘못되었습니다."),
    INVALID_TOKEN_ID(HttpStatus.BAD_REQUEST, 40012, "잘못된 토큰 ID 형식입니다."),

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "인증되지 않은 사용자입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 40101, "만료된 토큰입니다."),
    UNKNOWN_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 40102, "알 수 없는 인증 토큰 오류가 발생했습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 40103, "리프레시 토큰이 유효하지 않습니다."),

    // 404 Not Found
    AUTH_HEADER_NOT_FOUND(HttpStatus.NOT_FOUND, 40403, "Authorization 헤더가 존재하지 않습니다."),
    AUTH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 40404, "AccessToken 또는 RefreshToken 값이 존재하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND_IN_STORE(HttpStatus.NOT_FOUND, 40405, "저장소에서 리프레시 토큰을 찾을 수 없습니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "서버 내부 오류입니다."),
    INVALID_SECRET_KEY(HttpStatus.INTERNAL_SERVER_ERROR, 50001, "JWT Secret Key 가 유효하지 않습니다."),
    TOKEN_HASHING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50002, "토큰 해시 생성에 실패했습니다."),
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