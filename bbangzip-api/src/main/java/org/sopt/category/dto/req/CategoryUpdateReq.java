package org.sopt.category.dto.req;

import jakarta.validation.constraints.NotBlank;
import org.sopt.category.domain.CategoryColor;

public record CategoryUpdateReq(
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        String name,
        CategoryColor color,
        boolean isStopped
) {}