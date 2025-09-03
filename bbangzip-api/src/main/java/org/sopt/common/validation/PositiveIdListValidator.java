package org.sopt.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

// 커스텀 검증 로직
public class PositiveIdListValidator implements ConstraintValidator<PositiveIdList, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        // 리스트가 null이거나 비어있을 경우 false를 반환
        if (value == null || value.isEmpty()) {
            return false;
        }

        return value.stream()
                .allMatch(id -> id instanceof Long && (Long) id > 0);
    }
}
