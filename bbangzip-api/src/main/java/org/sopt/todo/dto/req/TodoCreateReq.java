package org.sopt.todo.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record TodoCreateReq(
        @NotNull @Positive Long categoryId,
        @Size(min = 1, message = "내용은 최소 1자 이상이어야 합니다.")
        String content,
        LocalDate targetDate,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime
) {}