package org.sopt.todo.dto.res;

public record TodoDeleteRes(
        int completedCount,
        int totalCount
) {
}