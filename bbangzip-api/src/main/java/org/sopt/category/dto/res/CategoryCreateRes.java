package org.sopt.category.dto.res;

import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;

public record CategoryCreateRes(
    Long categoryId,
    String name,
    CategoryColor color,
    boolean isStopped
) {
  public static CategoryCreateRes from(Category category) {
    return new CategoryCreateRes(
        category.getId(),
        category.getName(),
        category.getColor(),
        category.isStopped()
    );
  }
}
