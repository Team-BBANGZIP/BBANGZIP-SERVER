package org.sopt.category.dto.response;

import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;

public record CategoryResponse(
        Long categoryId,
        String name,
        CategoryColor color,
        boolean isVisible
) {
    public static CategoryResponse from(CategoryEntity category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.isVisible()
        );
    }
}
