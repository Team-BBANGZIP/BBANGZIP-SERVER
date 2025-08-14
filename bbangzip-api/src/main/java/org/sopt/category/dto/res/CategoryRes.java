package org.sopt.category.dto.res;

import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;

public record CategoryRes(
        Long categoryId,
        String name,
        CategoryColor color,
        boolean isVisible
) {
    public static CategoryRes from(CategoryEntity category) {
        return new CategoryRes(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.isVisible()
        );
    }
}
