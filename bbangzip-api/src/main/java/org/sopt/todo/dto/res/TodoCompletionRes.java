package org.sopt.todo.dto.res;

public record TodoCompletionRes(
        Long todoId,
        boolean isCompleted,
        int completedCount,
        int totalCount
) {
}