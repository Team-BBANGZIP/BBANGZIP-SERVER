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
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 40402, "카테고리를 찾을 수 없습니다.");

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

