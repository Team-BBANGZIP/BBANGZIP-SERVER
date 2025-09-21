package org.sopt.category.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.category.domain.CategoryColor;

public record CategoryUpdateReq(
        @NotNull(message = "카테고리 이름은 필수입니다.")
        @Size(max = 25, message = "카테고리 이름은 25자 이내여야 합니다.")
        String name,
        CategoryColor color,
        boolean isStopped
) {}