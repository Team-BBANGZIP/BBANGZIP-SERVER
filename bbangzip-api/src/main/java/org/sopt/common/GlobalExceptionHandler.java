package org.sopt.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.sopt.code.ErrorCode;
import org.sopt.code.GlobalErrorCode;
import org.sopt.exception.BaseException;
import org.sopt.exception.BbangzipAuthException;
import org.sopt.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BbangzipAuthException.class)
    public ResponseEntity<BaseResponse<Void>> handleAuthException(BbangzipAuthException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[AuthException] {} - {}", errorCode.getMessage(), e.getMessage());

        return ResponseEntity
            .status(e.getStatus())
            .body(BaseResponse.fail(errorCode));
    }

    // @Valid 실패 시 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("[ValidationException] {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage())
        );

        return ResponseEntity
            .status(GlobalErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
            .body(BaseResponse.fail(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(), errors));
    }

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<BaseResponse<Void>> handleNoPageFoundException(Exception e) {
        ErrorCode errorCode = e instanceof HttpRequestMethodNotSupportedException
            ? GlobalErrorCode.METHOD_NOT_ALLOWED
            : GlobalErrorCode.NOT_FOUND_END_POINT;

        log.warn("[NoHandlerFound] {} - {}", errorCode.getMessage(), e.getMessage());

        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(BaseResponse.fail(errorCode));
    }

    // 필수 요청 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("[MissingServletRequestParameterException] {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.fail(GlobalErrorCode.INVALID_PARAMETER));
    }

    // JSON 파싱 실패 (ex: 잘못된 형식, enum 값 오류 등) 시 처리하는 예외 핸들러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e) {
        log.warn("[HttpMessageNotReadableException] {}", e.getMessage());

        // 내부에 중첩된 BaseException (InvalidCategoryColorException) 찾기
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof BaseException baseException) {
                log.warn("[HttpMessageNotReadableException - Nested BaseException] {}", baseException.getMessage());
                return ResponseEntity
                    .status(baseException.getStatus())
                    .body(BaseResponse.fail(baseException.getErrorCode()));
            }
            cause = cause.getCause();
        }

        // 기본 처리
        return ResponseEntity
            .status(GlobalErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
            .body(BaseResponse.fail(GlobalErrorCode.INVALID_INPUT_VALUE));
    }

    // BaseException (모든 커스텀 예외) 처리 핸들러
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException e) {
        log.warn("[BaseException] {} - {}", e.getErrorCode().getMessage(), e.getMessage());

        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(BaseResponse.fail(e.getErrorCode()));
    }

    // 기본 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e){
        log.error("[UnhandledException]", e);

        return ResponseEntity
            .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
            .body(BaseResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }
}