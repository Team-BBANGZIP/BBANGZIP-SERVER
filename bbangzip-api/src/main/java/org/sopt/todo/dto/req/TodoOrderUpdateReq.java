package org.sopt.todo.dto.req;

import jakarta.validation.constraints.NotNull;
import org.sopt.common.validation.PositiveIdList;

import java.util.List;

public record TodoOrderUpdateReq(
        @NotNull Long todoId,
        @NotNull Long originCategoryId,
        @NotNull Long targetCategoryId,
        @NotNull String targetCategoryColor,
        @NotNull @PositiveIdList
        List<Long> todoList
) {}