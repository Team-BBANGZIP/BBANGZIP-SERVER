package org.sopt.todo.dto.res;

public record TodoUpdateContentRes(
        Long todoId,
        String content
) {}