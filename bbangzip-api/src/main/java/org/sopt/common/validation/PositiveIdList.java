package org.sopt.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 커스텀 어노테이션 정의
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveIdListValidator.class)
public @interface PositiveIdList {

    String message() default "ID 리스트에는 음수 값이 포함될 수 없습니다.";  // 기본 에러 메시지

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
