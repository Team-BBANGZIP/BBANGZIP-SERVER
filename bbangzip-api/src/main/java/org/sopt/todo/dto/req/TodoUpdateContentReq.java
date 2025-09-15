package org.sopt.todo.dto.req;

import jakarta.validation.constraints.NotNull;

public record TodoUpdateContentReq(
        @NotNull(message = "투두 내용은 필수 값입니다.")
        String content
) {}