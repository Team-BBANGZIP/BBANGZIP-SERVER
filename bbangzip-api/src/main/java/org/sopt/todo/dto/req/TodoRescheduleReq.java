package org.sopt.todo.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record TodoRescheduleReq(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate targetDate
){}