package org.sopt.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.code.ErrorCode;
import org.sopt.code.SuccessCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T>(
        int code,
        T data,
        String message
) {
    public static <T> BaseResponse<T> success(SuccessCode code, final T data) {
        return new BaseResponse<>(code.getCode(), data, null);
    }

    public static <T> BaseResponse<T> fail(ErrorCode code) {
        return new BaseResponse<>(code.getCode(), null, code.getMessage());
    }

    // @Valid 실패 시
    public static <T> BaseResponse<T> fail(int code, final T data) {
        return new BaseResponse<>(code, data, null);
    }
}