package org.sopt.category.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {

  private final Long id;
  private final Long userId;
  private final String name;
  private final CategoryColor color;
  private final Boolean isVisible;
  private final int order;

  // Entity → Domain
  public static Category fromEntity(CategoryEntity entity) {
    return Category.builder()
        .id(entity.getId())
        .userId(entity.getUser().getId())
        .name(entity.getName())
        .color(entity.getColor())
        .isVisible(entity.getIsVisible())
        .order(entity.getOrder())
        .build();
  }

}
