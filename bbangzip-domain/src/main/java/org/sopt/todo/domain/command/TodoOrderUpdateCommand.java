package org.sopt.todo.domain.command;

import java.util.List;

public record TodoOrderUpdateCommand(
        Long todoId,
        Long originCategoryId,
        Long targetCategoryId,
        String targetCategoryColor,
        List<Long> todoList
) {}