package org.sopt.todo.dto.res;

import org.sopt.category.domain.CategoryColor;

import java.util.List;

public record TodoListRes(
        String commitmentMessage,
        TodoSummary todoSummary,
        List<Category> categories
) {
    public record TodoSummary(
            String date,
            int totalCount,
            int completedCount
    ) {}
    public record Category(
            Long categoryId,
            String categoryName,
            String categoryColor,
            List<Todo> todos
    ) {
        public record Todo(
                Long todoId,
                String content,
                boolean isCompleted,
                String startTime
        ) {}
    }
}