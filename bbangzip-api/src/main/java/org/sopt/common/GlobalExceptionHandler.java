package org.sopt.common;

import lombok.extern.slf4j.Slf4j;
import org.sopt.code.ErrorCode;
import org.sopt.code.GlobalErrorCode;
import org.sopt.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // 기본 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e){
        log.error("[UnhandledException]", e);

        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(BaseResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }
}