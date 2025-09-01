package org.sopt.todo.dto.res;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.sopt.todo.domain.Todo;
import java.time.LocalDate;
import java.time.LocalTime;

public record TodoCreateRes(
        Long todoId,
        String content,
        LocalDate targetDate,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,
        boolean isCompleted,
        Long categoryId,
        String categoryColor
) {
    public static TodoCreateRes from(Todo todo) {
        return new TodoCreateRes(
                todo.getId(),
                todo.getContent(),
                todo.getTargetDate(),
                todo.getStartTime(),
                todo.isCompleted(),
                todo.getCategory().getId(),
                todo.getCategory().getColor().name()
        );
    }
}