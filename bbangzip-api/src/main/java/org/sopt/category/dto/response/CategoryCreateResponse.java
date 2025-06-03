package org.sopt.category.dto.response;

import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;

public record CategoryCreateResponse(
    Long categoryId,
    String name,
    CategoryColor color,
    Boolean isVisible
) {
  public static CategoryCreateResponse from(Category category) {
    return new CategoryCreateResponse(
        category.getId(),
        category.getName(),
        category.getColor(),
        category.getIsVisible()
    );
  }
}
