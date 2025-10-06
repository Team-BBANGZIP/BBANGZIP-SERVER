package org.sopt.todo.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoRepeatReq {
    @NotNull(message = "targetDate는 필수값입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
}