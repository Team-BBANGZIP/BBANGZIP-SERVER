package org.sopt.todo.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record TodoUpdateStartTimeReq(
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime
) {}