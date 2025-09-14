package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoRetriever todoRetriever;
    private final TodoSaver todoSaver;

    public TodoEntity saveTodo(
            Long categoryId,
            String content,
            LocalDate targetDate,
            LocalTime startTime,
            boolean isCompleted,
            int order
    ) {
        return todoSaver.save(categoryId, content, targetDate, startTime, isCompleted, order);
    }

    public int getTodoCountByCategoryAndDate(Long categoryId, LocalDate targetDate) {
        return todoRetriever.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }

    public List<Todo> getTodosByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date) {
        return todoRetriever.findTodosByCategoryIdsAndDate(categoryIds, date);
    }
}
