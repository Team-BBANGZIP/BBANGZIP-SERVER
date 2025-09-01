package org.sopt.todo.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.category.domain.Category;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class Todo {

    private final Long id;
    private final Category category;
    private final String content;
    private final LocalTime startTime;
    private final boolean isCompleted;
    private final LocalDate targetDate;
    private final int order;

    // Entity → Domain
    public static Todo fromEntity(TodoEntity entity) {
        return Todo.builder()
                .id(entity.getId())
                .category(entity.getCategory().toDomain())
                .content(entity.getContent())
                .startTime(entity.getStartTime())
                .isCompleted(entity.getIsCompleted())
                .targetDate(entity.getTargetDate())
                .order(entity.getOrder())
                .build();
    }
}
