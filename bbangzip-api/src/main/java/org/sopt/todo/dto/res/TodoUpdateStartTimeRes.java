package org.sopt.todo.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record TodoUpdateStartTimeRes(
        Long todoId,
        @JsonFormat(pattern = "HH:mm")
        LocalTime  startTime
) {}