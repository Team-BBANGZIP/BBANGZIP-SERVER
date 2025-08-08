package org.sopt.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.category.domain.CategoryColor;

public record CategoryCreateRequest(
    @NotBlank(message = "카테고리 이름은 필수입니다.")
    String name,

    @NotNull(message = "카테고리 색상은 필수입니다.")
    CategoryColor color
) {}
