package org.sopt.category.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryCoreErrorCode implements ErrorCode {

  DUPLICATED_CATEGORY(HttpStatus.CONFLICT, 40901, "이미 존재하는 카테고리입니다."),
  INVALID_CATEGORY_COLOR(HttpStatus.BAD_REQUEST, 40002, "지원하지 않는 색상입니다."),
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 40402, "카테고리를 찾을 수 없습니다."),
  INVALID_CATEGORY_ORDER(HttpStatus.BAD_REQUEST, 40003, "카테고리 순서 정보가 유효하지 않습니다."),
  INVALID_CATEGORY_ORDER_MISMATCH(HttpStatus.BAD_REQUEST, 40004, "카테고리 수와 요청된 ID 수가 일치하지 않습니다."),
  INVALID_CATEGORY_ORDER_DUPLICATE(HttpStatus.BAD_REQUEST, 40005, "중복된 ID가 존재합니다."),
  INVALID_CATEGORY_ORDER_MISSING(HttpStatus.BAD_REQUEST, 40006, "누락된 ID가 존재합니다.");

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

