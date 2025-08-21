package org.sopt.category.dto.res;

import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;

public record CategoryRes(
        Long categoryId,
        String name,
        CategoryColor color,
        boolean isStopped
) {
    public static CategoryRes from(Category category) {
        return new CategoryRes(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.isStopped()
        );
    }
}
