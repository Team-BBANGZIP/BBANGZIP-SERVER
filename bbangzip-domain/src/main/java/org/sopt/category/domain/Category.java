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
  private final boolean isStopped;
  private final int order;

  // Entity → Domain
  public static Category fromEntity(CategoryEntity entity) {
    return Category.builder()
        .id(entity.getId())
        .userId(entity.getUser().getId())
        .name(entity.getName())
        .color(entity.getColor())
        .isStopped(entity.isStopped())
        .order(entity.getOrder())
        .build();
  }

    public Category update(String newName, CategoryColor newColor, boolean newIsStopped, int newOrder) {
        return Category.builder()
                .id(this.id)  // 기존 id 유지
                .userId(this.userId)  // 기존 userId 유지
                .name(newName)
                .color(newColor)
                .isStopped(newIsStopped)
                .order(newOrder)
                .build();
    }

}
